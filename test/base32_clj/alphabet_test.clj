(ns ^{:doc "Testing the Base32 Alphabet and Conversions"
      :author "wang wu tao"}
  base32-clj.alphabet-test
  (:use midje.sweet
        base32-clj.alphabet))

(fact "about alphabets"
      (count BASE32_DEFAULT) => 32
      (count BASE32_DECODE) => 128)

(let [values (range 32)]
  (fact "about encoding/decoding symmetry."
        (let [encoded (map #(int->base32-byte %) values)
              decoded (map #(base32-byte->int %) encoded)]
          encoded => (contains [(byte \2) (byte \3)] :gaps-ok)
          encoded =not=> (contains [(byte \0) (byte \1)] :gaps-ok)
          decoded => (just values))))