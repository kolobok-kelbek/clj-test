(ns clj-test.model.patient
  (:use [korma.core :only [defentity table entity-fields pk]])
  (:require [schema.core :as schema]))

; -- Korma configuration
(defentity patient
  (table :patients) ; Associated table
  (pk :id) ; primary key
  (entity-fields :id :full_name :gender :dob :address :oms_number)) ; Default field for select

(def patient-schema
  {:id schema/Int
   :full_name schema/Str
   :gender (schema/enum "male" "female")
   :dob schema/Inst
   :address (schema/maybe schema/Str)
   :oms_number schema/Str
   :updated_at (schema/maybe schema/Inst)
   :created_at schema/Inst})

(def patient-schema-view
  {:full_name schema/Str
   :gender (schema/enum "male" "female")
   :dob schema/Inst
   (schema/optional-key :address) (schema/maybe schema/Str)
   :oms_number schema/Str})

(def patient-schema-view-identified
  {:id schema/Int
   :full_name schema/Str
   :gender (schema/enum "male" "female")
   :dob schema/Inst
   (schema/optional-key :address) (schema/maybe schema/Str)
   :oms_number schema/Str})

(defn to-view-identified
  [data]
  (let [view {:id (get data :id)
   :full_name (get data :full_name)
   :gender (get data :gender)
   :dob (get data :dob)
   :address (get data :address)
   :oms_number (get data :oms_number)
  }] view))
