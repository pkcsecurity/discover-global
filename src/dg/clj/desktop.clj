(ns dg.clj.desktop
  (:require [hiccup.core :as html]
            [garden.core :as css]
            [dg.clj.index :as idx]))

(def desktop-css
  (css/css 
    {:pretty-print? false}
    [:html {:font-size "100%"}]
    [:.transition-slow {:transition "all 0.5s ease-in-out"}]
    [:.bg-black {:background-color :black}]
    [:#remoteVideos :video {:height "600px"}]))

(defn style [href]
  [:link 
   {:rel "stylesheet" 
    :href href}])

(def index
  (html/html {:mode :html}
             [:head
              [:meta {:charset "utf-8"}]
              [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
              [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
              (style "https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css")
              (style "https://cdnjs.cloudflare.com/ajax/libs/basscss/8.0.4/css/basscss.min.css")
              (style "https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css")
              (style "https://use.typekit.net/pfn3cyx.css")
              (style "https://use.fontawesome.com/releases/v5.1.1/css/all.css")
              [:style idx/core-css]
              [:style desktop-css]
              [:title "Discover Global"]]
             [:body
              [:div#app]
              [:script {:src 
                        (if (= "production" (System/getenv "RING_ENV"))
                          "/js/release/index.js"
                          "/js/development/index.js")}]
              [:script {:async :async :src "webrtc.js"}]]))
