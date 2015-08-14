(ns spekl-package-manager.check
  (:require [clojure.java.io :as io]
            [clojure.tools.logging :as log]
            [spekl-package-manager.templates :as templates]

            ))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Prove the equivalence of two implementations of a program in C ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Required config elements:
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; reference:
;;   file : ref.c
;;   function: my_function
;; test:
;;   file : test.c
;;   function: my_function

(defn reference-cfg [] (*check-configuration* :reference))
(defn reference-file [] ((reference-cfg) :file))
(defn reference-function [] ((reference-cfg) :function))

(defn test-cfg [] (*check-configuration* :test))
(defn test-file [] ((test-cfg) :file))
(defn test-function [] ((test-cfg) :function))

(defn proof-script [name t1 t2] (str "proof_" name "_" t1 "_" t2 ".saw"))

(defcheck equiv-c []

  ;; step 1 - llvm compile the two input files.
  (log/info "[check:saw.equiv-c] LLVM Compiling reference and test files...")
  (run "clang" "-c" "-emit-llvm" "-o" (str (reference-function) ".bc") (reference-file))
  (run "clang" "-c" "-emit-llvm" "-o" (str (test-function) ".bc") (test-file))

  ;; step 2 - create the proof script
  (log/info "[check:saw.equiv-c] Creating proof script...")

  (spit (proof-script "equivalence" (reference-function) (test-function))
        (templates/template-with-params
         (resolve-path "proof_equivalence.saw.ftl")
         {
          "reference_function" (reference-function)
          "reference_file" (reference-file)
          "test_function" (test-function)
          "test_file" (test-file)
          }))

  ;; step 3 - run saw on the proof script
  (log/info "[check:saw.equiv-c] Executing proof...")
  (run "${saw-dist/bin/saw}" (proof-script "equivalence" (reference-function) (test-function)))
  
  ;; step 4 - remove the object code, source and the proof script
  (log/info "[check:saw.equiv-c] Cleaning up...")
  (run "rm" (str (reference-function) ".bc"))
  (run "rm" (str (test-function) ".bc"))
  (run "rm" (proof-script "equivalence" (reference-function) (test-function))))
  


(defcheck default (equiv-c))





