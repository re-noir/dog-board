(ns dog-board.server
  (:require [noir.server :as server]
            [korma.db :refer :all])
  (:gen-class))


(defdb dev (mysql {:host "localhost"
                   :db "dogpigdb"
                   :user "dogpig"
                   :password "dogpig"
                   :useSSL false}))
                  

(server/load-views-ns 'dog-board.views)

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "9090"))]
    (server/start port {:mode mode
                        :ns 'dog-board})))

