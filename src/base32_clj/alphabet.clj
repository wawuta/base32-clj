(ns ^{:doc "Alphabets for Base32 Enoding/Decoding."
      :author "wang wu tao"}
  base32-clj.alphabet)

;; ## Alphabet
(def ^:const ^String BASE32_DEFATLT
  "Base32 Alphabet."
  "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567")

(def ^:const BASE32_PAD (byte \=))

(defmacro ^:private generate-decode-table
  []
  (concat
   (repeat 50 0xFF)
   (range 0x1A 0x20)
   (repeat 9 0xFF)
   (range 0x00 0x1A)
   (repeat 6 0xff)
   (range 0x00 0x1A)
   (repeat 5 0xff)
   ))

(def ^:const BASE32_DECODE
  (generate-decode-table))

;; ## Conversion

(defmacro int->base32-byte
  "Convert 5-bit integer to Base32 byte."
  [i]
  `(byte (int (.charAt BASE32_DEFATLT (int ~i)))))

(defmacro base32-byte->int
  "Convert Base32 charater to 5-bit integer."
  [c]
  `(get BASE32_DECODE ~c))