(ns clj-test.controller.patient-controller
  (:use [ring.util.response :only [response]])
  (:require [compojure.core :refer :all]
            [ring.util.http-response :refer :all]
            [compojure.route :as route]
            [clojure.java.jdbc :as jdbc]
            [ring.middleware.json :as ring-json]
            [clj-test.service.patient-service :as patient-service]
            [clj-test.model.patient :as patient]
            [clj-test.config.db :as database]))

(defn get-all []
  (response
      (patient-service/get-all)))

