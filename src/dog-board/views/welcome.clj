(ns dog-board.views.welcome
  (:require [dog-board.views.common :as common]
            [noir.content.getting-started]
            [korma.core :rename {update kupdate}
             :refer :all]
            [hiccup.page :refer [include-js]]
            [noir.response])
  (:use [noir.core :only [defpage parse-args]]))

(defentity dog)

(defpage "/pdf/preview" []
  (common/layout
   :content
   [[:div
     [:a.btn.btn-success {:href "/sample10.pdf" :target "_blank"}
      "PDF 열기"]]]))


(defpage "/bbbb" []
  (common/layout
   :content
   [[:h1 "Hello"]]))
      
(defpage "/foo" []
  (common/layout
   :content
   [[:h1 "Hello"]]))

(defpage "/react" []
  (common/layout
   :script
   [(include-js "/js/main.min.js?v=7")]
   :content
   [[:div#react-content]]))

(defn select-dog [id name limit-n]
  (let [query (cond-> (select* dog) 
                (-> id nil? not)
                (where
                 (> :id id))
                
                (-> name empty? not)
                (where
                 (like :name
                       (sqlfn "concat" name "%")))

                (-> limit-n nil? not)
                (limit limit-n))]
  (select query)))
  

(defpage "/dogs" {:keys [id name limit]}
  (noir.response/json
   (select-dog id
               name
               limit)))

;; (defpage "/dog" {:keys [id name]}
;;   (let [query (cond-> (select* dog) 
;;                 (-> id empty? not)
;;                 (where
;;                  (= :id id))
                
;;                 (-> name empty? not)
;;                 (where
;;                  (like :name
;;                        (sqlfn "concat" name "%"))))]
;;     (println (as-sql query))            
;;     (common/layout
;;      :content
;;      [[:div
;;        [:h1 "Hello World"]
;;        [:form {:action "/bar"}
;;         [:input.form-control {:name "id" :value id}]
;;         [:input.form-control {:name "name" :value name}]
;;         [:button.btn.btn-info "OK"]]
;;        [:table.table
;;         [:thead
;;          [:tr.animated.bounceIn
;;           [:th "Id"] [:th "Name"] [:th "Description"]]
;;          [:tbody
;;           (map (fn [row]
;;                  (let [k [:id :name :description]]
;;                    [:tr
;;                     (map (fn [col]
;;                            [:td (row col)])
;;                          k)]))
;;                (select query))]]]
;;        [:div
;;         [:form {:action "/add-dog" :method "post"}
;;          [:label.label {:for "id"} "ID"]
;;          [:input.form-control {:name "name" :id "id"}]
;;          [:input.form-control {:name "description"}]
;;          [:button.btn.btn-info "Add"]]]
;;        ]])))

(defpage [:get "/add-dog"] {:as new-dog}
  (insert dog (values [new-dog]))
  (noir.response/status 200 ""))


(defpage [:get "/remove-dog"] {:keys [id]}
  (delete dog (where (= :id id)))
  (noir.response/status 200 ""))

(defpage [:post "/add-dog"] {:as new-dog}
  (insert dog (values [new-dog]))
  (noir.response/redirect "/bar"))

(defpage "/aaaa" []
  (common/layout
   :content
   [[:h1 "Good!!"]
    [:button.btn.btn-info.btn-lg "OK"]]))

;; (defn find-type [c]
;;   (let [cnt (count c)]
;;     (if (not= (inc cnt)
;;               (count (conj c [:a 1])))
      

(defn remove-first [c lst]
  (let [[h t] (split-with #(not= % c) lst)]
    (concat h (rest t))))

(defn perms [lst]
  (if (empty? lst)
    [[]]
    (mapcat (fn [current]
              (map (fn [result]
                     (cons current result))
                   (perms (remove-first current lst))))
            lst)))

(defpage "/dog-sample" []
  (common/layout
   :content
   [[:table.table
     [:tbody 
      (let [dogs (select dog)]
        (for [dog dogs]
          [:tr
           [:td (:id dog)]
           [:td (:name dog)]
           [:td (:description dog)]]))]]]))
      
