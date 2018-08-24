(ns dg.clj.index
  (:require [hiccup.core :as html]
            [garden.core :as css]))

(def pri "rgb(91, 194, 231)")
(def sec "rgb(44, 117, 172)")
(def success "rgb(122, 154, 1)")
(def fail "rgb(37, 48, 62)")
(def fail-alt "rgb(247, 174, 102)")
(def dis "rgb(211, 239, 239)")

(def core-css
  (css/css 
    {:pretty-print? false}
    [:* {:box-sizing :border-box}]
    [:html
     {:font-size "130%"
      :font-family ["'freight-sans-pro'" "sans-serif"]}]
    [:.sans {:font-family ["'freight-sans-pro'" "sans-serif"]}]
    [:.serif {:font-family ["'bressay-display'" "sans-serif"]}]
    [:h1 :h2 :h3 :h4 :h5 :h6
     {:line-height 1.3
      :font-family ["'bressay-display'" "serif"]}]
    [:h1 {:font-size "3.157em"}]
    [:h2 {:font-size "2.369em"}]
    [:h3 {:font-size "1.777em"}]
    [:h4 {:font-size "1.333em"}]
    [:h5 {:font-size "1em"}]
    [:h6 {:font-size "0.75em"}]
    [:p {:line-height "1.27"}]
    [:.transition {:transition [[:all "0.2s" "ease-in-out"]]}]
    [:.border-wide {:border-width "3px"}]
    [:.border-super-wide {:border-width "5px"}]
    [:.gray {:color "#ddd"}]
    [:.bg-gray {:background-color "#ddd"}]
    [:.border-gray {:border-color "#ddd"}]
    [:.black {:color "#000"}]
    [:.bg-black {:color "#000"}]
    [:.white {:color "#fff"}]
    [:.border-white {:color "#fff"}]
    [:.bg-white {:background-color "#fff"}]
    [:.success {:color success}]
    [:.bg-success {:background-color success}]
    [:.border-success {:border-color success}]
    [:.fb {:color "rgb(59, 89, 152)"}]
    [:.goog {:color "rgb(219, 50, 54)"}]
    [:.link {:color sec
             :text-decoration :none
             :border-bottom "1px dotted"}]
    [:.dis {:color dis}]
    [:.bg-dis {:background-color dis}]
    [:.border-dis {:border-color dis}]
    [:.fail {:color fail-alt}]
    [:.bg-fail {:background-color fail-alt}]
    [:.border-fail {:border-color fail-alt}]
    [:.pri {:color pri}]
    [:.bg-pri {:background-color pri}]
    [:.border-pri {:border-color pri}]
    [:.sec {:color sec}]
    [:.bg-sec {:background-color sec}]
    [:.border-sec {:border-color sec}]))

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
              [:style core-css]
              [:title "Discover Global"]]
             [:body
              [:div#app]
              [:script {:src 
                        (if (= "production" (System/getenv "RING_ENV"))
                          "/js/release/index.js"
                          "/js/development/index.js")}]]))
