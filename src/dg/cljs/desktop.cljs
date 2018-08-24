(ns dg.cljs.desktop
  (:require [reagent.core :as r]))

(def app-state (r/atom :homepage))

(defn nav []
  [:div.border-bottom.border-gray.px2.flex.items-center.justify-between.fixed.top-0.left-0.right-0.bg-white.z2
   {:style {:height "75px"}}
   [:img {:height "30"
          :src "imb.png"}]
   [:div.flex.justify-center.items-center
    [:div.bg-pri.white.p1 {:on-click #(reset! app-state :video)}
     [:p "Chat now"]]
    [:div.flex.justify-center.items-center.ml2
     [:i.fas.fa-user-circle.fa-2x.pr1]]]])

(def progress (r/atom 0.54))

(defn progress-percent [percent]
  ^{:key percent}
  [:div.absolute.top-0.left-0.z1.center.h2
   {:class (if (< percent 50)
             "animated fadeIn"
             "animated flash")
    :style {:height "100px"
            :line-height "95px"
            :width "100px"
            :font-weight 700}}
   (str (* 100 percent) "%")])

(defn radial []
  [:div.relative {:style {:height "100px"
                          :width "100px"}}

   [progress-percent @progress]
   [:svg.absolute.top-0.left-0.transition-slow
    {:width 100
     :height 100
     :viewBox "0 0 100 100"
     :style {:transform "rotate(-90deg)"}}
    [:circle {:cx 50
              :cy 50
              :r 45
              :fill :none
              :stroke "#f0f0f0"
              :stroke-width "10"}]
    [:circle.transition-slow
     {:cx 50
      :cy 50
      :r 45
      :fill :none
      :stroke "rgb(122,154,1)"
      :stroke-width "10"
      :stroke-dasharray "314.15926"
      :stroke-dashoffset (str (* 360 (- 1 @progress)))}]]])

(defn sidenav []
  [:div.border-right.border-gray.py2 {:style {:flex 2}}
   [:div.flex.justify-center.items-center
    [radial]]
   [:div
    [:img {:on-click #(reset! app-state :medical-qs)
           :src (case @app-state
                  :homepage "progress.svg"
                  :cleared "progress3.svg"
                  :complete "progress3.svg"
                  "progress2.svg")
           :width "320"
           :style {:transform "scale(1.2)"
                   :margin-top "80px"
                   :margin-left "15px"}}]]])

(defn update-progress! []
  (reset! progress 0.70))

(defn main [end?]
  [:div.animated.fadeIn
   [:h2 (if end? 
          "On to the next task!"
          "Welcome back!")]
   (if end?
     [:p "Take a look at your next Top Task below to keep making progress."]
     [:p "This is your "
      [:span.bold "Discover Global Assessment Portal"]
      ". From here, you can see your status in each phase of the full process."])
   [:div.mt4
    [:div.mb1
     [:h2.sans "Top Five Tasks"]]
    [:div.mb1
     [:h4 "1. " [:a.link {:on-click #(reset! app-state :medical-qs)
                          :href "#"} 
                 (if end? 
                   "Field Questionnaire"
                   "Medical Questionnaire")]]]
    [:div.mb1 
     [:h4 "2. " [:a.link {:href "#"} "Lifestyle and Wellness Questionnaire"]]]
    [:div.mb1 
     [:h4 "3. " [:a.link {:href "#"} "Church Interview"]]]
    [:div.mb1 
     [:h4 "4. " [:a.link {:href "#"} "Pastor Scorecard"]]]
    [:div.mb1 
     [:h4 "5. " [:a.link {:href "#"} "Additional Family Information"]]]]])

(defn medical-qs []
  [:div 
   [:h2.mb2 "Medical Clearance"]
   [:p.max-width-2 
    "Medical clearance is required of all applicants and their immediate family members (spouse and children). Due to the unique challenges presented by overseas service, we want to identify and address any health related issues that could potentially impact timing, placement, ongoing treatment, and access to good medical care. The medical evaluation process will be thorough because our desire is for those we send to be successful in their service and confident in their care. "]
   [:p.mt2 [:i.fas.fa-stopwatch.mr1] "Time to complete: 1.5 hours"]
   [:div.bg-pri.white.px2.py1.inline-block.mt3
    {:on-click #(reset! app-state :medical-info)}
    [:h4.sans "Start Questionnaire"]]])

(defn medical-info []
  [:div 
   [:h2 "Medical Clearance"]
   [:h5.mb2 [:i.fa.fa-info-circle.pri {:style {:margin-right "5px"}}] 
    "A new item requires attention."]
   [:p.max-width-2 
    "Thank you for completing the questionnaire! In reviewing your submission we noticed you have a history of lower back complications. As a result, we would appreciate additional information."]
   [:h4.mt3.mb2 "Here's your next step:"]
   [:div.mb1 "1. Make an appointment with a back specialist."]
   [:div.mb1 "2. Afterwards, please have your doctor send your record/report to:"
    [:div.serif.ml2.mb2
     [:br]
     "Travel Health America"
     [:br]
     "221 S 11th St Ste 1, Bismarck, ND"]]
   [:div.mb1 "3. Don’t worry about cost! " [:a.link {:href "#"} "We are here to help."]]
   [:div.bg-pri.white.px2.py1.inline-block.mt3
    {:on-click #(reset! app-state :medical-gotcha)}
    [:h4.sans "Appointment Scheduled"]]])

(defn medical-gotcha []
  [:div 
   [:h2 "Medical Clearance"]
   [:h5.mb2 
    [:i.fa.fa-map-marked.fail {:style {:margin-right "5px"}}]
    "A new item may require a change of preferences."]
   [:p.max-width-2 
    "Thank you for following up with a back specialist. We have received and reviewed the updated records. Based on what we learned, we may need to redirect you from the location you initially indicated on your application. It is our desire to see you be successful if you are sent to the field and this condition can put you at risk for further complications."]
   [:div.bg-sec.white.px2.py1.inline-block.mt3
    [:h4.sans "I understand, let's continue."]]
   [:div.bg-pri.white.px2.py1.inline-block.mt3.ml2
    {:on-click #(reset! app-state :scheduler)}
    [:h4.sans "I want to discuss more."]]])

(defn calendar []
  (let [selected-day (r/atom nil)
        availables #{21 23 27 30}
        selected-time (r/atom nil)]
    (fn []
      (let [sday @selected-day
            stime @selected-time]
        [:div.flex.animated.fadeIn
          [:div {:style {:flex 1}}
           [:h4.center.mb2 "August 2018"]
           [:div.col-12.flex.border-bottom.border-gray.mb1
            {:style {:height "2em"
             :font-size "0.8em"}}
            [:div {:style {:flex 1}} "Su"]
            [:div {:style {:flex 1}} "M"]
            [:div {:style {:flex 1}} "T"]
            [:div {:style {:flex 1}} "W"]
            [:div {:style {:flex 1}} "Th"]
            [:div {:style {:flex 1}} "F"]
            [:div {:style {:flex 1}} "Sa"]]
           (for [row (range 0 5)]
             ^{:key row} 
             [:div.col-12.flex
              (for [col (range 0 7)]
                (let [day (- (+ (* 7 row) col) 2)]
                  ^{:key col} 
                  [:div.relative
                   {:on-click #(when (availables day)
                                 (reset! selected-time nil)
                                 (reset! selected-day day))
                    :style {:height "50px" :flex 1}}
                   (when (pos? day)
                     [:div.serif.p1.absolute.top-0.left-0
                      {:style {:font-size "0.8em"}
                       :class (if (< day 17) "gray")}
                      day])
                   (when (availables day)
                     [:div.absolute.top-0.left-0.right-0.bottom-0.flex.justify-center.items-center
                      [:i.fa.fa-comment-alt.transition
                       {:class (if (= sday day)
                                 "sec"
                                 "pri")}]])]))])]
            [:div.center {:style {:flex 1}}
             [:div
              (when sday
                [:div.animated.fadeIn
                 [:h4.center.mb2 (str "August " sday " Times")]
                 [:div.max-width-1.mx-auto
                  (for [h (case sday 
                            21 ["11:30-12:00" "12:00-12:30" "1:30-2:00"]
                            23 ["10:00-10:30"]
                            27 ["12:00-12:30" "3:30-4:00"]
                            30 ["11:00-11:30" "11:30-12:00" "12:00-12:30" "4:30-5:00"])]
                    ^{:key h} 
                    [:div.mb1.px2.py1.transition.border
                     {:on-click #(reset! selected-time h)
                      :class (if (= h stime) 
                               "bg-sec white border-sec"
                               "border-pri pri")}
                     h])
                  (when stime
                    [:div.mb1.p2.transition.bg-sec.white.animated.fadeIn
                       {:on-click #(reset! app-state :video)}
                      "Confirm Appointment"])]])]]]))))

(defn medical-scheduler []
  (let [item (r/atom nil)]
    (fn []
      [:div 
       [:h2 "Medical Clearance"]
       [:h4.mb2 "Let's find a time and place to discuss."]
       [:p.max-width-2 
        "Provide us with the ability to schedule an appointment with your Assessment 
        Consultant and any other appropriate consultant (e.g. Medical, Wellness, TCK)."]
       [:div.bg-pri.white.px2.py1.inline-block.mt3
        {:on-click #(reset! item :video)
         :class (if (= :video @item) "bg-sec" "bg-pri")}
        [:h4.sans [:i.fa.fa-video.mr1] "Video"]]
       [:div.bg-pri.white.px2.py1.inline-block.mt3.ml2
        {:on-click #(reset! item :phone)
         :class (if (= :phone @item) "bg-sec" "bg-pri")}
        [:h4.sans [:i.fa.fa-mobile.mr1] "Phone"]]
       [:div.bg-pri.white.px2.py1.inline-block.mt3.ml2.mb3
        {:on-click #(reset! item :email)
         :class (if (= :email @item) "bg-sec" "bg-pri")}
        [:h4.sans [:i.fa.fa-envelope.mr1] "Email"]]
       (when @item
         [calendar])])))

(defn video-controls []
  [:div.white.absolute.bottom-0.left-0.p2.flex.z4
   {:on-click #(reset! app-state :cleared)}
   [:i.fa.fa-volume-up.fa-2x.mx2]
   [:i.fa.fa-volume-off.fa-2x.mx2]
   [:i.fa.fa-microphone-slash.fa-2x.mx2]
   [:div.bg-pri.p2.inline-block "End the Call"]])

(defn video []
  (let []
    (r/create-class
      {:component-did-mount
       (fn [this]
         (let [webrtc (js/SimpleWebRTC. #js {:localVideoEl "localVideo"
                                             :remoteVideosEl "remoteVideos"
                                             :autoRequestMedia true})]
           (.on webrtc 
                "readyToCall" 
                #(.joinRoom webrtc "kjlasfd89asdf78h12h"))))

       :reagent-render
      (fn []
        [:div#video.bg-black.fixed.top-0.left-0.bottom-0.right-0.z3.overflow-hidden
         [video-controls]
         [:video#localVideo.absolute.bottom-0.right-0.z4 {:style {:height "150px"}}]
         [:div#remoteVideos.absolute.top-0.left-0.bottom-0.right-0.z3.flex.justify-around.items-center]])})))

(defn feedback-icon [icon]
  (let [clicked? (r/atom false)]
    (fn [icon]
      [:i.far.fa-4x.mx1
       {:on-click (fn []
                    (reset! clicked? true)
                    (js/setTimeout #(reset! app-state :complete)
                                   1500))
        :class (str icon
                    (when @clicked? " pri animated flash"))}])))

(defn feedback []
  [:div.my3.animated.fadeIn
   [:h4.mb2 "How was your Medical Clearance experience?"]
   [:div.flex
    [feedback-icon "fa-grin-beam"]
    [feedback-icon "fa-grin"]
    [feedback-icon "fa-meh"]
    [feedback-icon "fa-angry"]
    [feedback-icon "fa-tired"]]
   [:div.mt2
    [:a.link {:href "#"}
     "Want to leave a comment?"]]])

(defn cleared []
  (let [feedback? (r/atom false)]
    (r/create-class
      {:component-did-mount
       (fn [_]
         (.log js/console "mounted")
         (js/setTimeout (fn []
                          (update-progress!)
                          (reset! feedback? true)) 2000))

       :reagent-render
       (fn [] 
         [:div
          [:h2 "Medical Clearance"]
          [:h5.mb2.animated.flash.success
           [:i.fa.fa-check {:style {:margin-right "5px"}}]
           "Way to go! You’ve completed your Medical Evaluation!"]
          [:p.max-width-2
           "Thank you for completing the Medical Clearance. You are now 
           ready to move on to the Field Questionnaire.
           Please tell us about your Medical Clearance experience before continuing."]
          (when @feedback?
            [feedback])])})))

(defn body []
  [:div 
   [nav]
   [:div.flex 
    {:style {:padding-top "75px"
             :width "100%"}}
    [sidenav]
    [:div.p3 {:style {:flex 7}}
     (case @app-state
       :homepage [main false]
       :medical-qs [medical-qs]
       :medical-info [medical-info]
       :medical-gotcha [medical-gotcha]
       :scheduler [medical-scheduler]
       :video [video]
       :cleared [cleared]
       :complete [main true])]]])

