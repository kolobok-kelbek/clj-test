(ns app.core
    (:require
      [reagent.core :as r :refer [atom]]
      [reagent.dom :as d]
      [app.pages.home :as home]))

(defn mount-root []
  (d/render [home/view] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
