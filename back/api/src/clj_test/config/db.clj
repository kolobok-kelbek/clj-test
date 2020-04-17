(ns clj-test.config.db
  (:use [korma.db]))

(defdb db (postgres {:host "db"
                     :port 5432
                     :db "dev"
                     :user "dev"
                     :password "dev"}))
