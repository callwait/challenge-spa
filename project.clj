(defproject scramble-spa "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [reagent "0.7.0"]
                 [compojure "1.6.1"]
                 [ring "1.7.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-mock "0.3.2"]
                 [cljs-ajax "0.7.5"]
                 [re-frame "0.10.5"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-ring "0.12.1"]]
  :min-lein-version "2.5.3"
  :source-paths ["src/clj" "src/cljs"]
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler scramble-spa.core/dev-app}
  :ring {:handler scramble-spa.core/dev-app}
  :profiles
  {:dev  {:dependencies [[binaryage/devtools "0.9.10"]]
          :plugins      [[lein-figwheel "0.5.16"]]}
   :prod {}}
  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "scramble-spa.core/mount-root"}
     :compiler     {:main                 scramble-spa.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            scramble-spa.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]})
