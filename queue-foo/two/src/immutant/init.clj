(ns immutant.init
  (:require [immutant.messaging :as msg]))

(def q "/queue/foo")
(msg/start q, :durable false)

(defn handler [msg]
  (println "2" msg)
  (Thread/sleep (+ 1000 (rand 1000)))
  (msg/publish q (str "2 " (java.util.Date.))))

(msg/listen q handler)

(msg/publish q "start")