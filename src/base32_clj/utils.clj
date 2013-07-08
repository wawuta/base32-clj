(ns ^{:do "Utilty Functions for Base32 Encoding/Decoding"
      :author "wang wutao"}
  base32-clj.utils)

(set! *unchecked-math* true)

;; ## Constants
(def ^:const FIVE_MASK (int 0x1F))
(def ^:const OCTET_MASK (int 0xFF))

;; ## Shorthands
(defmacro <<
  [x n]
  `(int (bit-shift-left (int ~x) (int ~n))))

(defmacro >>
  [x n]
  `(int (bit-shift-right (int ~x) (int ~n))))

;; ## Length Calculation

(defmacro encode-result-size
  "Given the length of an UTF-8 byte array, compute the size of the Base32 result array."
  [n]
  `(int (* (/ (- (+ ~n 4) (mod (+ ~n 4) 5)) 5) 8)))

(defmacro decode-result-size
   "Given the length of a Base32-encoded UTF-8 byte array, compute the maximum size of the decoding result array."
  [n]
  `(int (/ (* ~n 5) 8)))

;; ## Data Access

(defmacro ->int
  [b]
  `(let [b# (byte ~b)]
     (int
      (if (< b# (int 0))
        (unchecked-add-int b# (int 256))
        b#))))

(defmacro ->byte
  [b]
  `(let [b# (int ~b)]
     (byte
      (if (> b# (int 127))
        (unchecked-subtract-int b# (int 256))
        b#))))

(defmacro get-byte-at
  [s idx offset]
  `(byte (aget ~s (unchecked-add-int (int ~idx) (int ~offset)))))

(defmacro get-five
  [i n]
  `(byte
    (bit-and
     (bit-shift-right ~i (int ~(* (- 7 n) 5)))
     FIVE_MASK)))

(defmacro get-octet
  [i n]
  `(->byte
    (bit-and
     (bit-shift-right ~i (int ~(* (- 4 n) 8)))
     OCTET_MASK)))