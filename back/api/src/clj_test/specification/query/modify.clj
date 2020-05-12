(ns clj-test.specification.query.modify
  (:require [honeysql.core :as sql]
            [honeysql.helpers :as h]
            [honeysql-postgres.format :refer :all]
            [honeysql-postgres.helpers :as psqlh]))

(defn insert-single-query
  [table fields data]
  (-> (h/insert-into table)
    (h/values [(fields)])
    (psqlh/returning :*)
    (sql/format :params data)))

(defn update-by-id-query
  [table fields id data]
  (-> (h/update table)
    (h/sset (fields))
    (h/where [:= :id :?id])
    (psqlh/returning :*)
    (sql/format :params data)))

(defn delete-query
  [table id]
  (-> (h/delete-from table)
    (h/where [:= :id :?id])
    (sql/format :params {:id id})))

