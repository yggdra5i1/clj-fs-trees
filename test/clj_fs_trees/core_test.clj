(ns clj-fs-trees.core-test
  (:require [clojure.test :refer :all]
            [clj-fs-trees.core :refer :all]))

(deftest build
  (testing "build"
    (let [tree (mkdir "/" [(mkdir "etc") (mkdir "usr") (mkfile "robots.txt")])
          actual {:name "/"
                  :children [{:name "etc"
                              :children []
                              :meta {}
                              :type "directory"}
                             {:name "usr"
                              :children []
                              :meta {}
                              :type "directory"}
                             {:name "robots.txt"
                              :meta {}
                              :type "file"}]
                  :meta {}
                  :type "directory"}]
      (is (= tree actual)))))

(deftest is-file
  (testing "file?"
    (let [node (mkfile "config.json")]
      (is (file? node))
      (is (not (dir? node))))))

(deftest is-dir
  (testing "dir?"
    (let [node (mkdir "/")]
      (is (dir? node))
      (is (not (file? node))))))

(deftest test-map
  (testing "map"
    (let [tree (mkdir "/" [(mkdir "eTc" [(mkdir "NgiNx") (mkdir "CONSUL" [(mkfile "config.json")])])
                           (mkfile "hOsts")])
          actual (tree-map (fn [n] (merge n {:name (clojure.string/upper-case (get-name n))})) tree)
          expected {:name "/"
                    :children [{:name "ETC"
                                :children [{:name "NGINX"
                                            :children []
                                            :meta {}
                                            :type "directory"}
                                           {:name "CONSUL"
                                            :children [{:name "CONFIG.JSON"
                                                        :meta {}
                                                        :type "file"}]
                                            :meta {}
                                            :type "directory"}]
                                :meta {}
                                :type "directory"}
                               {:name "HOSTS"
                                :meta {}
                                :type "file"}]
                    :meta {}
                    :type "directory"}]
      (is (= actual expected)))))
