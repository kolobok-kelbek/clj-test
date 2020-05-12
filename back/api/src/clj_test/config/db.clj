(ns clj-test.config.db
  (:use [clojure.java.shell :only [sh]])
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.java.jdbc :as jdbc]))

(def spec
  (->> "db.edn" io/resource slurp edn/read-string))

(defn query
  [q]
  (jdbc/query spec q))

(defn exec
  [q]
  (jdbc/execute! spec q))

