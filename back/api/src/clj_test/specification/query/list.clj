(ns clj-test.specification.query.list
  (:require [honeysql.core :as sql]
            [honeysql.helpers :as h]))

(defn get-sort-asc-list-query
  [table offset limit]
  (-> (h/select :*)
    (h/from table)
    (h/order-by [:id :asc])
    (h/offset :?offset)
    (h/limit :?limit)
    (sql/format :params {:offset offset :limit limit})))

