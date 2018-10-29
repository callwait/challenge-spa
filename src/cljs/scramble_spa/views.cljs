(ns scramble-spa.views
  (:require
    [re-frame.core :as rf]
    [scramble-spa.events :as events]
    [scramble-spa.subs :as subs]))

(defn main-panel []
  (let [str1   (rf/subscribe [::subs/challenge :str1])
        str2   (rf/subscribe [::subs/challenge :str2])
        result (rf/subscribe [::subs/challenge-result])]
    [:div
     [:h1 "Scramblies challenge SPA"]
     [:div "Input 1: "
      [:input {:type      "text"
               :on-change #(rf/dispatch [::events/set-challenge :str1 (-> % .-target .-value)])}]]
     [:div "Input 2: "
      [:input {:type      "text"
               :on-change #(rf/dispatch [::events/set-challenge :str2 (-> % .-target .-value)])}]]

     [:p "Scrambling "
      @str1
      (when @str2
        (str " and " @str2))]

     [:p "Result: " (if @result
                     [:b "TRUE"]
                     [:i "false"])]
     [:button {:on-click #(rf/dispatch [::events/scramble-it])} "Scramble it!"]]))
