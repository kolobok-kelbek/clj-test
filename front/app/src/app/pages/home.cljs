(ns app.pages.home
    (:require
      [reagent.core :as r :refer [atom]]
      [app.utils.bootstrap-wrapper :as b]
      [app.components.patients-table :as patients-table]))

(defn view
  []
  (fn []
    [:> b/container
      [:h1.mt-5.mb-5 "Patient manager"]
      [patients-table/view]]))

