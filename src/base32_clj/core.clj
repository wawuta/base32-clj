(ns ^{:doc "Base32 Encoding/Deonding in Clojure."
      :author "wang wutao"}
  base32-clj.core
  (:use base32-clj.alphabet
        base32-clj.utils)
  (:import java.nio.ByteBuffer))

(set! *unchecked-math* true)
(set! *warn-on-reflection* true)

;; ## Encoding

(defmacro write-base32
  [b o0 o1 o2 o3 o4]
  (let (v (gensym "v"))
   `(let [~v (long
               (bit-or
                 (<< (->int ~o0) 32)
                 ~(if o1 `(<< (->int ~o1) 24) 0)
                 ~(if o2 `(<< (->int ~o2) 16) 0)
                 ~(if o3 `(<< (->int ~o3) 8) 0)
                 ~(if o4 `(->int ~o4) 0)))]
      (doto ~b
        (.put (int->base32-byte (get-five ~v 0)))
        (.put (int->base32-byte (get-five ~v 1)))
        (.put ~(if o1 `(int->base32-byte (get-five ~v 2)) `BASE32_PAD))
        (.put ~(if o1 `(int->base32-byte (get-five ~v 3)) 'BASE32_PAD))
        (.put ~(if (and o1 o2) `(int->base32-byte (get-five ~v 4)) `BASE32_PAD))
        (.put ~(if (and o1 o2 o3) `(int->base32-byte (get-five ~v 5)) `BASE32_PAD))
        (.put ~(if (and o1 o2 o3) `(int->base32-byte (get-five ~v 6)) `BASE32_PAD))
        (.put ~(if (and o1 o2 o3 o4) `(int->base32-byte (get-five ~v 7)) `BASE32_PAD))))))

(defmacro write-octets
  [buffer data idx n]
  `(condp == ~n
     1 (write-base32 ~buffer (get-byte-at ~data ~idx 0) nil nil nil nil)
     2 (write-base32 ~buffer (get-byte-at ~data ~idx 0) (get-byte-at ~data ~idx 1) nil nil nil)
     3 (write-base32 ~buffer (get-byte-at ~data ~idx 0) (get-byte-at ~data ~idx 1) (get-byte-at ~data ~idx 2) nil nil)
     4 (write-base32 ~buffer (get-byte-at ~data ~idx 0) (get-byte-at ~data ~idx 1) (get-byte-at ~data ~idx 2) (get-byte-at ~data ~idx 3) nil)
     5 (write-base32 ~buffer (get-byte-at ~data ~idx 0) (get-byte-at ~data ~idx 1) (get-byte-at ~data ~idx 2) (get-byte-at ~data ~idx 3) (get-byte-at ~data ~idx 4))))

(defn encode-bytes
  ^"[B"
  [^"[B" data]
  (let [len (int (count data))
        cap (int (encode-result-size len))
        b (ByteBuffer/allocate cap)]
    (loop [i (int 0)]
      (if (< i len)
        (let [next-i (+ i (int 5))
              n (if (<= next-i len) (int 5) (unchecked-remainder-int (unchecked-subtract-int len i) (int 5)))]
          (write-octets b data i n)
          (recur next-i))
        (.array b)))))

(defn encode
  "Encode the given String to its UTF-8 Base32 Representataion."
  ([^String s] (encode s "UTF-8"))
  ([^String s ^String encoding]
     (let [data (encode-bytes (.getBytes s encoding))]
       (String. data "UTF-8"))))


(defmacro read-base32
  [b s0 s1 s2 s3 s4 s5 s6 s7]
  `(let [v# (long (bit-or (<< ~s0 35) (<< ~s1 30) (<< (or ~s2 0) 25) (<< (or ~s3 0) 20) (<< (or ~s4 0) 15) (<< (or ~s5 0) 10) (<< (or ~s6 0) 5) (or ~s7 0)))]
     (.put ~b (get-octet v# 0))
     (when (and ~s2 ~s3)
       (.put ~b (get-octet v# 1))
       (when ~s4
         (.put ~b (get-octet v# 2))
         (when (and ~s5 ~s6)
           (.put ~b (get-octet v# 3))
           (when ~s7
             (.put ~b (get-octet v# 4))))))))

(defmacro read-five
  [buffer data idx]
  `(let [s0# (get-byte-at ~data ~idx 0)
         s1# (get-byte-at ~data ~idx 1)
         s2# (get-byte-at ~data ~idx 2)
         s3# (get-byte-at ~data ~idx 3)
         s4# (get-byte-at ~data ~idx 4)]
     (read-base32 ~buffer
         (base32-byte->int s0#))))