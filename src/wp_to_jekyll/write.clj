(ns wp-to-jekyll.write
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [me.raynes.fs :as fs])
  (:import (java.text SimpleDateFormat)))

(defn make-comment [{:keys [comment_content comment_date comment_author]}]
  (str "<div class='comment'>"
    comment_content
    "  <div class='by'>"
    comment_author " on " comment_date
    "  </div>"
    "</div>"))

(defn parse [{:keys [post_status post_title post_date post_author post_content]} users comments {:keys [output overwrite-files render-comments]} sub-dir]
  (println "parsing" post_status post_title "written on" (.toString post_date) "by" (:display_name (first (filter #(= post_author (:id %)) users))))
  (let [title        (-> (string/replace post_title #"[_\s]" "-")
                       (string/replace #"[?\.*&^%$#@!'`\\/\[\]\{\}+=\":]" "")
                       (.toLowerCase))
        post_title   (string/replace post_title #"[\"]" "\\\\\"")
        date         (.format (SimpleDateFormat. "yyyy-MM-dd") post_date)
        file-name    (str date "-" title ".md")
        full-name    (str output "/" sub-dir "/" file-name)
        exists       (.exists (io/file full-name))
        contents     (str "---\nlayout: post\ntitle: \"" post_title "\"\ndate: " post_date "\nauthor: " (:display_name (first (filter #(= post_author (:id %)) users))) "\ncategories: " "\n---\n" post_content)
        comment-text (if (and (> (count comments) 0) render-comments)
                       (str (reduce (fn [pv cv] (str pv "\n" (make-comment cv))) "\n\n<div class='archived comments'>\n" comments) "\n</div>")
                       "")]
    (if (or (not exists) overwrite-files)
      (do
        (io/make-parents full-name)
        (with-open [wr (io/writer full-name)]
          (.write wr (str contents comment-text))))
      (println "Did not overwrite " full-name))))


(defn move-includes [current new-location]
  (fs/copy-dir current new-location))