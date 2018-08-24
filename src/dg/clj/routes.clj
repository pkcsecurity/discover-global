(ns dg.clj.routes
  (:require [ring.middleware.content-type :as ct]
            [compojure.core :as r]
            [compojure.route :as route]
            [dg.clj.index :as index]
            [dg.clj.desktop :as dk]
            [hiccup.core :as html]
            [clj-http.client :as client]
            [cognitect.transit :as edn]
            [ring.middleware.format :as fmt]))

(defn index-response-fn [_]
  {:status 200 
   :headers {"Content-Type" "text/html"}
   :body index/index})

(defn desktop-response-fn [_]
  {:status 200 
   :headers {"Content-Type" "text/html"}
   :body dk/index})

(r/defroutes routes
  (r/GET "/" [] index-response-fn)
  (r/GET "/desktop" [] desktop-response-fn)
  (route/resources "/")
  (route/not-found nil))

(def app
  (-> routes
      (fmt/wrap-restful-format :formats [:transit-json])
      (ct/wrap-content-type)))
