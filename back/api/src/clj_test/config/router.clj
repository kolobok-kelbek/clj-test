(ns clj-test.config.router
  (:require [compojure.api.sweet :refer :all]
            [clojure.tools.logging :as log]
            [ring.util.http-response :refer :all]
            [clj-test.config.swagger :as swagger]
            [clj-test.controller.patient-controller :as patient-controller]
            [clj-test.model.patient :as patient-model]))

(def patient-routes
  (context "/api/patients" [] :tags ["api-patients"]
    (GET "/" []
      :query-params [{offset :- Long 0}
                     {limit :- Long 200}]
      :summary "Gets list available patients"
      :return [patient-model/patient-schema-view-identified]
      :responses {200 {:schema [patient-model/patient-schema-view-identified],
                       :description "List of patients"}}
      (log/info "offset - " offset)
      (log/info "limit - " limit)
      (patient-controller/get-list offset limit))
  (GET "/:id" []
      :path-params [id :- Long]
      :return patient-model/patient-schema-view
      :responses {200 {:schema patient-model/patient-schema-view-identified,
                       :description "The patient found"}
                  404 {:description "No patient found for this id"}}
      :summary "Gets a specific patient by id"
      (patient-controller/get-patient id))
  (POST "/" []
      :body [patient patient-model/patient-schema-view]
      :return patient-model/patient-schema-view-identified
      :responses {201 {:schema patient-model/patient-schema-view-identified,
                       :description "Returns the created patient"}
                  400 {:description "Malformed request body"}}
      :summary "Creates new patinets"
      (patient-controller/create patient))
  (PUT "/:id" []
      :path-params [id :- Long]
      :body [patient patient-model/patient-schema-view]
      :return patient-model/patient-schema-view-identified
      :responses {200 {:schema patient-model/patient-schema-view-identified,
                       :description "The updated patient"}
                  400 {:description "Malformed request body"}
                  404 {:description "No patient found for this id"}}
      :summary "Updates and existing patient by id"
      (patient-controller/upgrade id patient))
  (DELETE "/:id" []
      :path-params [id :- Long]
      :responses {204 {:description "Patient successfuly deleted"}
                  404 {:description "No patient found for this id"}}
      :summary "Delete patient by id"
      (patient-controller/delete id))
  ))

(defapi app-routes
   swagger/swagger-routes
   patient-routes
  (undocumented (compojure.route/not-found (ok {:not "found"}))))

