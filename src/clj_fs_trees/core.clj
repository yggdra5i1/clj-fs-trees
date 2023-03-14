(ns clj-fs-trees.core
  (:gen-class))

(defn mkfile
  ([name] {:name name
           :meta {}
           :type "file"})
  ([name meta] {:name name
                :meta meta
                :type "file"}))

(defn mkdir
  ([name] {:name name
           :children []
           :meta {}
           :type "directory"})
  ([name children] {:name name
                    :children children
                    :meta {}
                    :type "directory"})
  ([name children meta] {:name name
                         :children children
                         :meta meta
                         :type "directory"}))

(defn get-children [dir]
  (:children dir))

(defn get-meta [node]
  (:meta node))

(defn get-name [node]
  (:name node))

(defn file? [node]
  (= (:type node) "file"))

(defn dir? [node]
  (= (:type node) "directory"))

(defn tree-map [fn tree]
  (let [updated-node (fn tree)]
    (cond
      (dir? tree) (merge updated-node
                         {:children (map #(tree-map fn  %) (get-children tree))})
      :else updated-node)))
