(defproject dog-board "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [mysql/mysql-connector-java "5.1.42"]
                 [org.clojure/core.async "0.3.442"]
                 [noir "1.3.0-beta3"]
                 [korma "0.4.3"]
                 [buddy "1.3.0"]
                 [reagent "0.6.2"]
                 [cljs-ajax "0.6.0"]
                 [org.clojure/clojurescript "1.9.473"]]
                 ;; [quil "2.6.0"]
  :plugins [[lein-cljsbuild "1.1.5"]]
  :hooks [leiningen.cljsbuild]
  :cljsbuild {:builds [{:source-paths ["src-cljs"]
                        :compiler
                        {:output-to "resources/public/js/main.min.js"
                         :output-dir "resources/public/js/out"
                         :main       "dog-board/core"
                         :asset-path "js/out"
                         :optimizations :none
                         :pretty-print true}}]}
  :main dog-board.server
  :aot [dog-board.server])

