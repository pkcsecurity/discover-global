(defproject dg "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "LATEST"]
                 [cheshire "5.8.0"]
                 [org.immutant/web "LATEST"]
                 [ring/ring-core "LATEST"]
                 [ring/ring-devel "LATEST"]
                 [org.clojure/clojurescript "LATEST"]
                 [com.cognitect/transit-clj "0.8.309"]
                 [less-awful-ssl "1.0.3"]
                 [com.cognitect/transit-cljs "0.8.256"]
                 [garden "LATEST"]
                 [compojure "LATEST"]
                 [hiccup "LATEST"]
                 [reagent "LATEST"]
                 [org.clojure/core.async "0.4.474"]
                 [cljs-http "LATEST"]
                 [clj-http "LATEST"]
                 [ring-middleware-format "0.7.2"]
                 [lein-cljsbuild "LATEST"]
                 [cljsjs/chartjs "LATEST"]]
  :min-lein-version "2.7.1"
  :main ^:skip-aot dg.clj.core
  :profiles {:uberjar {:main dg.clj.core, :aot :all}}
  :plugins [[lein-cljsbuild "LATEST"]]
  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src/dg/cljs"]
                :compiler
                {:output-to "resources/public/js/development/index.js"
                 :source-map true
                 :output-dir "resources/public/js/development"
                 :optimizations :none
                 :main dg.cljs.core
                 :asset-path "js/development"
                 :infer-externs false
                 :cache-analysis true
                 :pretty-print true}}
               {:id "release"
                :source-paths ["src/dg/cljs"]
                :compiler
                {:output-to "resources/public/js/release/index.js"
                 :source-map "resources/public/js/release/index.js.map"
                 :externs ["externs/externs.js"]
                 :main dg.cljs.core
                 :output-dir "resources/public/js/release"
                 :optimizations :advanced
                 :pseudo-names false}}]})
