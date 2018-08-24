(ns dg.cljs.trunk
  (:require [reagent.core :as r]))

(def question-number (r/atom 0))

(defn next-q! []
  (swap! question-number inc))

(defn prev-q! []
  (swap! question-number dec))

(defn item [i ans]
  [:div.border-pri.border.mb1.py3.center
   [:p 
    [:span.pr1.serif]
    ans]])

(defn map-src [ne]
  (let [x (.-offsetX ne)
        y (.-offsetY ne)]
    (cond
      (< 0 x 100) 
      "SouthAmerica.svg"

      (and (< 75 x 150)
           (< 30 y))
      "SSA.svg"

      (and (< 200 x 300)
           (< 30 y)) 
      "EastAsia.svg"

      (or 
        (and (< 100 x 150)
             (< 0 y 100))
        (and (< 100 x 300)
             (< 0 y 30)))
      "Europe.svg"

      (and (< 200 x 200)
           (< 30 y 150))
      "CentralAsia.svg"
      
      :else 
      "NAME.svg")))

(defn clicked-next [clicked?]
  (fn []
    (reset! clicked? true)
    (js/setTimeout (fn []
                     (reset! clicked? false)
                     (next-q!))
                   300)))

(defn map-img [clicked?]
  (let [src (r/atom "World.svg")
        clicked-map? (r/atom false)]
    (fn []
      [:div
       [:div
        [:img {:on-click (fn [e]
                          (compare-and-set! clicked-map? false true)
                          (swap! src
                                 (fn [x]
                                   (or (map-src (.-nativeEvent e)) x)))
                          (.stopPropagation e))
              :src @src
              :width "300"
              :style {:transform "scale(1.5)"}}]]
       (when @clicked-map?
         [:div.mt4.animated.fadeIn
          [:a.link {:on-click (clicked-next clicked?)
                    :href "#"}
           "Click to continue"]])])))

(defn input-q [clicked?]
  (let [text (r/atom "")]
    (fn [clicked?]
      [:div.mx2 
       {:on-click #(.stopPropagation %)}
       [:input.border-none.px1.col-12
        {:value @text
         :on-change #(reset! text (.. % -target -value))
         :placeholder "Church Name"
         :style {:border-radius 0
                 :line-height "2em"
                 :font-size "1em"
                 :border-bottom "1px solid #ddd"}}]
       (when-not (= "" @text)
         [:div.mt4.animated.fadeIn
          [:a.link {:on-click (clicked-next clicked?)
                    :href "#"}
           "Click to continue"]])])))

(defn textarea-q [clicked?]
  (let [text (r/atom "")]
    (fn [clicked?]
      [:div.mx2
       {:on-click #(.stopPropagation %)}
       [:textarea.border.p1.col-12.border-gray.serif
        {:value @text
         :on-change (fn [e] (reset! text (.. e -target -value)))
         :placeholder "(Please describe in 540 characters or less)"
         :style {:line-height "1.2em"
                 :font-size "0.8em"}
         :rows 15}]
       (when-not (= "" @text)
         [:div.mt4.animated.fadeIn
          [:a.link {:on-click (clicked-next clicked?)
                    :href "#"}
           "Click to continue"]])])))

(defn question []
  (let [clicked? (r/atom false)]
    (fn [i q opts]
      [:div.center.mt3.px2
       {:class (if @clicked?
                 "animated fadeOutLeft"
                 "animated fadeInRight")}
       [:h4.mb3 q]
       [:div
        {:on-click (clicked-next clicked?)}
        (cond
          (coll? opts)
          (for [[i o] (map-indexed vector opts)]
            ^{:key o} [item i o])

          (= opts :map)
          [map-img clicked?]

          (= opts :input)
          [input-q clicked?]

          (= opts :textarea)
          [textarea-q clicked?])]])))

(def questions 
  [{:text "How long would you like to serve?" 
    :options ["Short-Term (Less Than 2 Months)"
              "Mid-Term (2 Months - 3 Years)"
              "Long-Term (3 Years+)"]}
   {:text "What do you want to do?"
    :options ["Tell People About Jesus"
              "Help Start a Church"
              "Support Missionary Teams"
              "Use My Professional Skills"
              "Other"]}
   {:text "What context do you want to work in?"
    :options ["Urban/City"
              "Rural"
              "Anywhere"]}
   {:text "Where would you go?"
    :options :map}
   {:text "I'm a US citizen"
    :options ["Yes"
              "No"]}
   {:text "What church are you a member of?"
    :options :input}
   {:text "What is your education level?"
    :options ["High School"
              "Undergraduate"
              "Graduate"
              "Seminary"]}
   {:text "Why do you want to be an IMB Missionary?"
    :options :textarea}])

(defn trunk-club []
  (let [i @question-number
        {:keys [text options]} (nth questions i nil)]
    [question i text options]))
