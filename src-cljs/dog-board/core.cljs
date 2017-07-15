(ns dog-board.core
  (:require [reagent.core :as r]
            [ajax.core :refer [GET POST json-response-format]]
            [dog-board.infinite-scroll :as i]))


(declare dogs)
(declare Table)
(declare Row)
(declare counting-component)
(declare remove-dog!)
(declare reset-dogs!)

(defonce search-name (r/atom ""))
(defonce click-count (r/atom 0))

(defn counting-component []
  [:div
   "The atom " [:code "click-count"] " has value: "
   @click-count ". "
   [:input {:type "button" :value "Click me!"
            :on-click #(swap! click-count inc)}]])

(defonce dogs (r/atom []))
(defonce new-dog (r/atom {:name "" :description ""}))

(defn Table [data col-options]
  [:table.table
   [:thead
    [:tr [:td "Foo"]]
    [:tr
     (map (fn [col-option]
            [:th (:attr col-option)
             (:label col-option)])
          col-options)]]
   [:tbody
    (map (fn [row]
           [Row row col-options])
         data)]])


(defn Row [row col-options]
  [:tr.animated.fadeInDown
   (map (fn [col-option]
          (let [k (:key col-option)
                label-fn (get col-option :label-fn)
                cell (get row k)]
            [:td (if label-fn
                   (label-fn cell row)
                   cell)]))
        col-options)])

(defn add-dog! [dog]
  (GET "/add-dog" {:params dog
                   :handler (fn [resp]
                              (reset-dogs!))}))
(defn remove-dog! [dog]
  (swap! dog
         #(remove
           (fn [row]
             (= (get dog :id)
                (get row :id))
             %))))

(defn reset-dogs! []
  (GET "/dogs" {:handler (fn [resp]
                           (reset! dogs resp)
                           #_(js/alert @dogs))
                :params {:name @search-name}
                :response-format :json
                :keywords? true})
  #_(swap! dogs conj [{:id 1
            :name "Kogi"
            :desc "smart"}
           {:id 1
 :name "Kogi"
            :desc "smart"}
           {:id 1
            :name "Kogi"
            :desc "smart"}]))

(defn app []
  [:div
   [:input.form-control {:default-value ""
                         :on-change (fn[e]
                                      (reset! search-name
                                              (-> e .-target .-value)))}]
   
   [:button.btn.btn-info 
    {:on-click #(reset-dogs!)}
    "load!"]
   [:div
    [:input.form-control {:default-value ""
                          :on-change (fn[e]
                                       (swap! new-dog
                                              assoc
                                              :name (-> e .-target .-value)))}]
    [:input.form-control {:default-value ""
                          :on-change (fn[e]
                                       (swap! new-dog
                                              assoc
                                              :description (-> e .-target .-value)))}]
    [:button.btn.btn-primary
     {:on-click
      (fn [e]
        (cond (empty? (get @new-dog :name))
              (js/alert "name required")

              (empty? (get @new-dog :description))
              (js/alert "description required")

              :else
              (add-dog! @new-dog)))}
     "Add!"]]
    #_[:div (str @dogs)]
   [Table @dogs
    [{:key :id
      :attr {}
      :label-fn (fn [v r] [:b v])
      :label "Id"}
     {:key :name
      :attr {}
      :label "Name"}
     {:key :description
      :attr {}
      :label-fn (fn [v r] [:i v])
      :label "Description"}
     {:key :remove
      :attr {}
      :label-fn (fn [v r]
                  [:button.btn.btn-danger
                   {:on-click
                    (fn [e]
                      (GET "/remove-dog"
                           {:params {:id (:id r)}
                            :handler
                            #(reset-dogs!)}))}
                   [:span.glyphicon.glyphicon-trash]])
                    
      :label "Remove!"}]]
   [i/infinite-scroll ]])
      

  
(r/render [app]
          (.getElementById js/document "react-content")
(reset-dogs!))
          ;; (.-body js/document)
(js/alert "aa")

