(ns dg.cljs.core
  (:require 
    [dg.cljs.trunk :as t]
    [dg.cljs.desktop :as dk]
    [reagent.core :as r]
    [goog.dom :as dom]))

(enable-console-print!)

(def app-state (r/atom :homepage))

(def nop (constantly nil))

(defn button [on-click text]
  (let []
    (fn [on-click text]
      [:button.p2.bg-sec.outline-none.border-none.white
       {:on-click (or on-click nop)}
       [:h3.sans text]])))

(defn nav []
  [:div.border-bottom.border-gray.px2.flex.items-center.justify-between 
   {:style {:height "75px"}}
   [:img {:height "30"
          :src "imb.png"}]
   [:div.bg-pri.white.p1
    [:p "Chat now"]]])

(defn hero []
  [:div.p2.py3.bg-pri.white {:style {:background "url('bg.jpg') left top"
                                     :background-size :cover}}
   [:h2.mb2 "Discover the nations."]
   [button 
    #(reset! app-state :create-profile)
    "Become an IMB Missionary"]])

(defn curious-diagram []
  (let [selected (r/atom :long)]
    (fn []
      [:div.border-top.border-gray.py2.center
       [:h3.mt2 "Curious?"]
       [:a.link {:style {:line-height "1.35em"} :href "#"} "Ask a consultant"]
       [:div
        [:div.flex.justify-around.mt3
         [:div.border.border-pri.pri.p1 
          {:style {:font-size "0.8em"} 
           :on-click #(reset! selected :long)}
          "Long-term"]
         [:div.border.border-pri.pri.p1 {:on-click #(reset! selected :mid)
                                         :style {:font-size "0.8em"}} 
          "Mid-term"]
         [:div.border.border-pri.pri.p1 {:on-click #(reset! selected :team)
                                         :style {:font-size "0.8em"}} 
          "Team Associate"]]
        [:img.animated.fadeIn {:src 
                               (case @selected
                                 :long "LongTermTimeline.svg"
                                 :mid "MidTermTimeline.svg"
                                 :team "TeamAssociate.svg")}]
        [:div.mt3.px2
         [:h4.mb1 "Long Term" [:br] "(3 years+)"]
         [:p.mb1 "Full-time Devotion to the Missionary Task"]
         [:p.mb1 "Fully-funded by IMB"]
         [:p.mb1 "12+ Seminary Hours Required"]]

        [:div.mt4.px2
         [:h4.mb1 "Mid Term" [:br] "(2 months to 3 Years)"]
         [:p.mb1 "Full-time Devotion to the Missionary Task"]
         [:p.mb1 "Fully-funded options available by IMB"]
         [:p.mb1 "Ex. IMB Field Support, Journeymen, College, Retirees"]]

        [:div.mt4.px2
         [:h4.mb1 "Team Associate" [:br] "(2 months to 3 Years)"]
         [:p.mb1 "Self Funded"]
         [:p.mb1 "Full/Part Time Devotion to Missionary Task"]
         [:p.mb1 "No Seminary Hours Required"]]]])))

(defn homepage []
  [:div.animated.fadeIn
   [nav]
   [hero]
   [curious-diagram]])

(defn input [placeholder type]
  [:input.border-gray.col-12.px1.mb1.border-none
   {:type type
    :placeholder placeholder
    :style {:border-radius 0
            :border-bottom "1px solid #ddd"
            :line-height "2.25em"
            :font-size "1em"}}])

(defn fb-button []
  [:div.fb.white.p2.sans.my1.border
   [:i.fab.fa-facebook.mr1]
   "Create with Facebook"])

(defn google-button []
  [:div.goog.white.p2.sans.my1.border
   [:i.fab.fa-google.mr1]
   "Create with Google"])

(defn create-profile-button []
  [:div.bg-pri.white.p2.sans.my1 
   {:on-click (fn []
                (reset! app-state :trunk-club))}
   "Create Profile"])

(add-watch t/question-number
           ::qn
           (fn [_ _ o n]
             (.log js/console o n)
             (when (= n 8)
               (reset! app-state :long-term))))
(defn create-profile []
  [:div.mt3.center.animated.fadeIn.max-width-1.px2
   [:img.mb1 {:style {:height "50px"}
              :src "imb.png"}]
   [:h4.mb2 "Let's get you started!"]
   [input "Name" :text]
   [input "Email" :email]
   [create-profile-button]
   [:div.my2 "or"]
   [fb-button]
   [google-button]])

(defn long-term []
  [:div.px2.center.py3
   [:h3.mb2 "Thank you!"]
   [:h4.mb3 "You may be a good fit for Long-term Service."]
   [:h6 "Click on your preferred path to continue."]
   [:img {:on-click #(reset! app-state :expect)
          :src "LongTermTimeline.svg"}]
   [:a.link {:href "#"} "Got Questions? We can help."]])

(defn accordion-item [icon title text]
  ^{:key icon}
  [:div.animated.fadeInRight
   [:h4.mb1 title]
   [:p.left-align text]])

(defn accordion [page]
  [:div.mt3
   (case @page
     0 [accordion-item
        "fa-church"
        "Spiritual" 
        "We are looking for people who are called by God, affirmed by their church leadership, and eager to engage in the missionary task. Applicants should be sharing their faith, making disciples, and in alignment with the Baptist Faith and Message."]
     1 [accordion-item 
        "fa-user-md"
        "Medical" 
        "Medical clearance is required of all applicants and their immediate family members. Due to the challenges presented by overseas service, we identify and address any health related issues that could impact timing, placement, ongoing treatment, and access to medical care. The medical evaluation will be thorough because our desire is for those sent to succeed in their service and in their care."]
     2 [accordion-item 
        "fa-child"
        "Children" 
        "We often send families with children. Children are active participants on missionary teams. We want them to flourish spiritually, emotionally, developmentally, and academically. As a result, children will be part of the assessment process in relation to the areas mentioned above. This could potentially impact timing and placement."]
     3 [accordion-item
        "fa-hand-holding-heart"
        "Lifestyle/Wellness"
        "We are not looking for perfect people. We know perfect people do not exist! But we are looking for people who are emotionally healthy, make wise lifestyle choices, and have strong relationships. Due to the unique challenges presented by overseas service, we want to identify and address any lifestyle and wellness related issues that could potentially impact timing, placement, and access to good support systems."]
     4 [accordion-item
        "fa-money-bill"
        "Financial"
        "Southern Baptists generously support missionaries sent by the IMB. This frees up team members to focus on the missionary task. As a result, we will evaluate any current debt (link to debt guidelines) and conduct a credit check."]
     5 [accordion-item
        "fa-balance-scale"
        "Background"
        "IMB conducts a background check on all missionary applicants."])])

(defn expect []
  (let [page (r/atom 0)]
    (fn []
      [:div.px2
       [:h3.mt2.px2 "Here's what to expect."]
       [:div.mt1.right-align
        [:a.link {:on-click (fn []
                              (if (= @page 5)
                                (reset! app-state :feedback)
                                (swap! page inc)))
                  :href "#"}
         (if (= @page 5)
           "Give us your feedback."
           "Next")]]
       [:div.px2
        [accordion page]]])))

(defn feedback-icon [icon]
  (let [clicked? (r/atom false)]
    (fn [icon]
      [:i.far.fa-4x.mb1
       {:on-click (fn []
                    (reset! clicked? true)
                    (js/setTimeout #(reset! app-state :thanks)
                                   1500))
        :class (str icon
                    (when @clicked? " pri animated flash"))}])))

(defn feedback []
  [:div.center.my3
   [:h4.mb3 "How has your experience been so far?"]
   [:div.flex.flex-column
    [feedback-icon "fa-grin-beam"]
    [feedback-icon "fa-grin"]
    [feedback-icon "fa-meh"]
    [feedback-icon "fa-angry"]
    [feedback-icon "fa-tired"]]
   [:div.mt2
    [:a.link{:href "#"}
     "Want to leave a comment?"]]])

(defn body []
  (if (= "/desktop" (.-pathname js/location))
    [dk/body]
    (case @app-state
      :homepage [homepage]
      :create-profile [create-profile]
      :trunk-club [t/trunk-club]
      :long-term [long-term]
      :expect [expect]
      :feedback [feedback]
      :thanks [:div.flex.justify-center.items-center.px2.center
               {:style {:height "100%"
                        :width "100%"}}
               [:h1.mb4 "Thank you!"]])))

(defn -main []
  (r/render-component [body]
                      (dom/getElement "app")))

(-main)
