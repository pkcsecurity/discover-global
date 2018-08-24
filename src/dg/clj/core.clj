(ns dg.clj.core
  (:gen-class)
  (:require [cheshire.core :as json]
            [immutant.web :as server]
            [dg.clj.routes :as r]))

(defn -main [& args]
  (let [host "127.0.0.1"
        port (or (System/getenv "PORT") "8080")]
    (if (= "production" (System/getenv "RING_ENV"))
      (server/run r/app :host host :port port)
      (server/run-dmc r/app :host host :port port))))
