(ns clj-test.model.patient
  (:use [korma.core :only [defentity table entity-fields pk]])
  (:require [schema.core :as schema]))

; -- Korma configuration
(defentity patient
  (table :patients) ; Associated table
  (pk :id) ; primary key
  (entity-fields :id :full_name :gender :dob :address :oms_number)) ; Default field for select

; -- Validation schema for complete patient
(def patient-schema
  {:id schema/Int
   :full_name schema/Str
   :gender schema/Str
   :dob schema/Inst
   :address schema/Str
   :oms_number schema/Str})

; -- Validation for input rest patient
(def patient-schema-rest-in
  {:title schema/Str 
   :full_name schema/Str
   :gender schema/Str
   :dob schema/Inst
   :address schema/Str
   :oms_number schema/Str})

