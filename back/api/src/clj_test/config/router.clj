(ns clj-test.config.router
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [clj-test.config.swagger :as swagger]
            [clj-test.controller.patient-controller :as patient-controller]
            [clj-test.model.patient :as patient-model]))

(def patient-routes
  (context "/api/patients" [] :tags ["api-patients"]
    (GET "/" []
          :summary "Gets all available patients"
          :return [patient-model/patient-schema]
          :responses {200 {:schema [patient-model/patient-schema],
                           :description "List of patients"}}
          (patient-controller/get-all))
  (GET "/:id" []
            :path-params [id :- String]
            :return patient-model/patient-schema
            :responses {200 {:schema patient-model/patient-schema,
                             :description "The patient found"}
                        404 {:description "No patient found for this id"}}
            :summary "Gets a specific patient by id"
            (patient-controller/get-patient (Integer/parseInt id)))
  ))

(defapi app-routes
   swagger/swagger-routes
   patient-routes
  (undocumented (compojure.route/not-found (ok {:not "found"}))))

