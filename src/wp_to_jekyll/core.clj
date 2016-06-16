(ns wp-to-jekyll.core
  (:require [wp-to-jekyll.db :as db]
            [wp-to-jekyll.write :as write])
  (:import (java.util Date)))

(defn- dequeue-posts [db posts users options location]
  (if-let [post (first posts)]
    (do
      (write/parse post users (db/comments db (:id post)) options location)
      (recur db (rest posts) users options location))
    (println "finished parsing")))

(defn- enqueue-posts [db options]
  (let [users (db/all-users db)]
    (println "parsing published posts")
    (dequeue-posts db (db/all-posts db "publish") users options "_posts")
    (println "finished parsing published posts")
    (println "parsing draft posts")
    (dequeue-posts db (db/all-posts db "draft") users options "_drafts")
    (println "finished parsing draft posts")))

(defn start [{:keys [output db-subprotocol db-url db-port db-name db-user db-password wp-uploads overwrite] :as options} start-time]
  (println "starting with options" options)
  (write/move-includes wp-uploads output)
  (enqueue-posts (db/mysql-db db-subprotocol db-url db-port db-name db-user db-password) options)
  (println "total time:" (- (.getTime (Date.)) start-time) "ms"))


