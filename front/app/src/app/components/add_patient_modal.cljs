(ns app.components.add-patient-modal
    (:require
      [reagent.core :as r :refer [atom]]
      [app.utils.bootstrap-wrapper :as b]
      [app.utils.patient-client :as pc]
      [app.utils.input-mask :as im]
      [app.utils.logger :as logger]))

(def show-modal-add-patient (r/atom false))

(def default-patient-form-data {:full_name nil
                                :gender nil
                                :dob nil
                                :address ""
                                :oms_number nil})

(def patient-form-data (r/atom default-patient-form-data))

(def validated (r/atom false))

(defn handleClose
  []
  (reset! validated false)
  (reset! patient-form-data default-patient-form-data)
  (reset! show-modal-add-patient false))

(defn handleShow
  []
  (reset! show-modal-add-patient true))

(defn is-patient-form-valid
  []
  (let [full_name (:full_name @patient-form-data)
        gender (:gender @patient-form-data)
        dob (:dob @patient-form-data)
        oms_number (:oms_number @patient-form-data)]
    (if
      (and
        (not
          (empty? full_name))
        (or
          (= gender "male")
          (= gender "female"))
        (not (empty? dob))
        (not= (re-matches #"[0-9]{16}" oms_number) nil)) true false)))

(defn handleFormSubmit
  [event successHandler]
  (.preventDefault event)
  (.stopPropagation event)
  (if (is-patient-form-valid)
    (pc/create-patient @patient-form-data (fn [r] (successHandler) (handleClose)))
    (reset! validated true)))

(defn form
  [successHandler]
  [:> b/form {:id "form-add-patient" :noValidate true :validated @validated :onSubmit #(handleFormSubmit % successHandler)}  
    [:> b/form.Group
      [:> b/form.Label "Full name"]
      [:> b/form.Control {:type "text"
                          :on-change #(swap! patient-form-data assoc :full_name (-> % .-target .-value))
                          :required true}]
      [:> b/form.Control.Feedback {:type "invalid"} "Please enter your full name"]]
    [:fieldset
      [:div
        [:> b/form.Label "Gender"]
        [:> b/form.Group
          [:> b/form.Check {:on-change #(swap! patient-form-data assoc :gender (if (= (-> % .-target .-value) "on") "male"))
                            :type "radio"
                            :label "Male"
                            :name "patient-gender"
                            :id "patient-gender-male"
                            :custom true
                            :inline true
                            :required true}]
          [:> b/form.Check {:on-change #(swap! patient-form-data assoc :gender (if (= (-> % .-target .-value) "on") "female"))
                            :type "radio"
                            :label "Female"
                            :name "patient-gender"
                            :id "patient-gender-female"
                            :custom true
                            :inline true
                            :required true}]
          [:> b/form.Control.Feedback {:type "invalid" :class (if (and @validated (= (:gender @patient-form-data) nil)) "d-block" "")} "Please choose your gender"]]]]
    [:> b/form.Group
      [:> b/form.Label "Date of Birth"]
      [:> b/form.Control {:type "date"
                          :on-change #(swap! patient-form-data assoc :dob (-> % .-target .-value))
                          :required true}]
      [:> b/form.Control.Feedback {:type "invalid"} "Please enter your date of birth"]]
    [:> b/form.Group
      [:> b/form.Label "Address"]
      [:> b/form.Control {:type "text"
                          :on-change #(swap! patient-form-data assoc :address (-> % .-target .-value))}]]
    [:> b/form.Group
      [:> b/form.Label "OMS number"]
      [im/InputMask {:class "form-control"
                  :mask "9999999999999999"
                  :maskChar ""
                  :pattern "\\d{16}"
                  :required true
                  :on-change #(swap! patient-form-data assoc :oms_number (-> % .-target .-value))}]
      [:> b/form.Control.Feedback {:type "invalid"} "Please enter your oms number (OMS number must contain 16 numbers)"]]])

(defn view
  [successHandler]
  [:> b/modal {:show @show-modal-add-patient :onHide handleClose}
    [:> b/modal.Header {:closeButton true}
      [:> b/modal.Title "Add patient"]]
    [:> b/modal.Body
      [form successHandler]]
    [:> b/modal.Footer 
      [:> b/button {:variant "secondary" :on-click handleClose} "Close"]
      [:> b/button {:variant "primary"
                    :form "form-add-patient"
                    :type "submit"} "Save"]]])

