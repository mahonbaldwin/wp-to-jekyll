(ns wp-to-jekyll.console
  (:require [clojure.tools.cli :refer [parse-opts] :as cli]
            [wp-to-jekyll.core :as core])
  (:gen-class)
  (:import (java.util Date)))

(def cli-options
  ;; An option with a required argument
  [["-o" "--output OUTPUT" "Root directory for output location. Default Current Directory."
    :default ""]
   [nil "--db-subprotocol SUBPROTOCOL" "Default \"mysql\""
    :default "mysql"]
   [nil "--db-url DB-URL" "URL database is running on. Default \"127.0.0.1\""
    :default "127.0.0.1"]
   [nil "--db-port DB-PORT" "Port database is running on. Default 3306"
    :default 3306
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   [nil "--db-name DB-NAME" "Name of database."]
   [nil "--db-user DB-USER" "DB user with permissions"]
   [nil "--db-password DB-PASSWORD" "Password for user"]
   [nil "--wp-uploads WP-UPLOADS" "location of wordpress uploads directory"]
   [nil "--overwrite-files OVERWRITE" "Whether to overwrite files already existing in directory. Default false."
    :default false
    :parse-fn #(Boolean/valueOf %)
    :validate [#(contains? #{true false} %) "Must be true or false."]]
   [nil "--render-comments RENDER-COMMENTS" "Whether to render comments. Default true."
    :default true
    :parse-fn #(Boolean/valueOf %)
    :validate [#(contains? #{true false} %) "Must be true or false."]]
   ["-h" "--help"]])


(defn -main [& args]
  (let [start-time (.getTime (Date.))
        options (cli/parse-opts args cli-options)
        errors (:errors options)]
    (if errors
      (println errors)
      (core/start (:options options) start-time))))
