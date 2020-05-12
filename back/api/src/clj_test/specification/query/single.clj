(ns clj-test.specification.query.single
  (:require [honeysql.core :as sql]
            [honeysql.helpers :as h]))

(defn get-count
  [table]
  (-> (h/select :%count.*)
    (h/from table)
    sql/format))

(defn get-by-id
  [table id]
  (-> (h/select :*)
    (h/from table)
    (h/where [:= :id :?id])
    (sql/format :params {:id id})))

