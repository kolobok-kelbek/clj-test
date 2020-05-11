(ns app.utils.input-mask
    (:require 
      [reagent.core :as r]
      cljsjs.react-input-mask))

(def InputMask (r/adapt-react-class js/ReactInputMask))

