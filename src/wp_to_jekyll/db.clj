(ns wp-to-jekyll.db
  (:require [clojure.java.jdbc :as jdbc]))

(defn mysql-db
  "Generates DB information"
  [sub-protocol url port db-name username user-password]
  {:subprotocol sub-protocol
   :subname (str "//" url ":" port "/" db-name)
   :user username
   :password user-password})

(defn all-posts
  [db type]
  (jdbc/query db ["select id, post_date, post_author, post_title, post_status, post_content from wp_posts where post_status = ?" type]))

(defn all-users
  [db]
  (jdbc/query db ["select id, display_name from wp_users"]))

(defn comments
  [db post-id]
  (jdbc/query db ["select comment_date, comment_content, comment_author from wp_comments where comment_post_ID = ? and comment_approved = 1 order by comment_date" post-id]))