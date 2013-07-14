(defproject base32-clj "0.1.0-SNAPSHOT"
  :description "Base32 Enoding/Deconding in Clojure"
  :url "http://github.com/wawuta/base32-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {:dev {:dependencies [[midje "1.5.1"]]
                   :plugins [[lein-midje "3.0.1"]]}})
