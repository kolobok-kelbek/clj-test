(ns clj-test.config.db
  (:use [clojure.java.shell :only [sh]])
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def db-spec
  (->> "db.edn" io/resource slurp edn/read-string))

(def common
  (->> "common.edn" io/resource slurp edn/read-string))

(defn reinit
  []
  (sh "lein" "with-profile" (get common :env) "flyway" "clean")
  (sh "lein" "with-profile" (get common :env) "flyway" "migrate"))

