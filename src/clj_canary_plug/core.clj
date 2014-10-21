(ns clj-canary-plug.core
  (:import [net.canarymod.plugin.Priority]
           [net.canarymod.logger.Logman]))

(defn handlePlayerConnected [hook] 
  (.info (net.canarymod.logger.Logman/getLogman "ClojurePlugin") "new player connected"))

(defn enablePlugin [plugin]
 (.registerHook plugin "net.canarymod.hook.player.ConnectionHook" handlePlayerConnected net.canarymod.plugin.Priority/NORMAL))
