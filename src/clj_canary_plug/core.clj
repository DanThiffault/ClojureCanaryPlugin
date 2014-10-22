(ns clj-canary-plug.core
  (:require [net.canarymod.plugin.clojure.core :refer [registerAll log]])
  (:import [net.canarymod.plugin.Priority]
           [net.canarymod.logger.Logman]))

(def config { 
             :hooks [["net.canarymod.hook.player.ConnectionHook" 'clj-canary-plug.core/handlePlayerConnected  net.canarymod.plugin.Priority/NORMAL]]
             :repl-port 7888
             })

(defn enablePlugin [plugin]
  (registerAll plugin config))

(defn handlePlayerConnected [hook]
  (log :info "New player connected"))

