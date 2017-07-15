(ns dog-board.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css include-js html5]]))


(defpartial layout [ & {:keys [style
                               script
                               header
                               content
                               footer]}]
  (html5
   [:head
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"}]

    [:title "Macadamia!"]
    (include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css")
    (include-js "//code.jquery.com/jquery-1.11.3.min.js")]
   [:body
    [:div#header `(~@header)]
    [:div#wrapper.container {:style "display: table; height: 100vh;"}
     [:div#content {:style "display: table-cell; vertical-align: middle;"} `(~@content)]]
    [:div#footer `(~@footer)]
    (include-js "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js")
    (include-css "https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css")
    `(~@script)
    ]))
