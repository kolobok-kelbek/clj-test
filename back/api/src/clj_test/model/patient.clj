(ns clj-test.model.patient
  (:require [schema.core :as schema])
  (:import [java.time LocalDate]))

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
   :dob LocalDate
   (schema/optional-key :address) (schema/maybe schema/Str)
   :oms_number schema/Str})

(def patient-schema-view-identified
  {:id schema/Int
   :full_name schema/Str
   :gender (schema/enum "male" "female")
   :dob LocalDate
   (schema/optional-key :address) (schema/maybe schema/Str)
   :oms_number schema/Str})

(def meta-view
  {:total schema/Int
  })

(def patient-list
  {:data [patient-schema-view-identified]
   :meta meta-view
  })

(defn to-view-identified
  [data]
  (let [view {:id (get data :id)
   :full_name (get data :full_name)
   :gender (get data :gender)
   :dob (LocalDate/parse (.format (java.text.SimpleDateFormat. "yyyy-MM-dd") (get data :dob)))
   :address (get data :address)
   :oms_number (get data :oms_number)
  }] view))
