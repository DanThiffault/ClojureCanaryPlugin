(ns clj-canary-plug.core
  (:require [net.canarymod.plugin.clojure.core :refer [registerAll log] :as canary])
  (:import [net.canarymod.plugin.Priority]
           [net.canarymod.logger.Logman]))

(def config { 
             :hooks [["net.canarymod.hook.player.ConnectionHook" 'clj-canary-plug.core/handlePlayerConnected  net.canarymod.plugin.Priority/NORMAL]]
             :commands [[{:aliases ["foo.startrepl"]} 'clj-canary-plug.core/start-repl false]
                        [{:aliases ["foo.stoprepl"]} 'clj-canary-plug.core/stop-repl false]]
             :repl-port 7888
             })

(def plugin (ref nil)) 

(defn enablePlugin [p]
  (dosync (ref-set plugin p))
  (registerAll @plugin config))

(defn handlePlayerConnected [hook]
  (log :info "new player connected"))

(defn start-repl-internal
  ([cmd] (start-repl-internal cmd (:repl-port config)))
  ([cmd port] (canary/start-repl @plugin (Integer. port))))

(defn start-repl [[caller parameters]] (apply start-repl-internal parameters))

(defn stop-repl [_] (canary/stop-repl @plugin))
