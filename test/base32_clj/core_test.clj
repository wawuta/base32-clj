(ns ^{:doc "Testing Base32 encoding."
      :author "wang wutao"}
  base32-clj.core-test
  (:use midje.sweet
        base32-clj.core))

(fact "about encoding"
  (encode "") => ""
  (encode "f") => "MY======"
  (encode "fo") => "MZXQ===="
  (encode "foo") => "MZXW6==="
  (encode "foob") => "MZXW6YQ="
  (encode "fooba") => "MZXW6YTB"
  (encode "foobar") => "MZXW6YTBOI======"
  (encode "sure.") => "ON2XEZJO"
  (encode "sure") => "ON2XEZI="
  (encode "sur") => "ON2XE==="
  (encode "su") => "ON2Q===="
  (encode "leasure.") => "NRSWC43VOJSS4==="
  (encode "easure.") => "MVQXG5LSMUXA===="
  (encode "asure.") => "MFZXK4TFFY======"
  (encode "sure.") => "ON2XEZJO"
  (encode (str "a")) => "ME======"
  (encode "12345") => "GEZDGNBV"
  (encode "abcde") => "MFRGGZDF"
  (encode "1234567890") => "GEZDGNBVGY3TQOJQ"
  (encode "Twas brillig, and the slithy toves") => "KR3WC4ZAMJZGS3DMNFTSYIDBNZSCA5DIMUQHG3DJORUHSIDUN53GK4Y="
  )

(fact "about decoding"
  (decode "") => ""
  (decode "MY======") => "f"
  (decode "MZXQ====") => "fo"
  (decode "MZXW6===") => "foo"
  (decode "MZXW6YQ=") => "foob"
  (decode "MZXW6YTB") => "fooba"
  (decode "MZXW6YTBOI======") => "foobar"
  (decode "ON2XEZJO") => "sure."
  (decode "ON2XEZI=") => "sure"
  (decode "ON2XE===") => "sur"
  (decode "ON2Q====") => "su"
  (decode "NRSWC43VOJSS4===") => "leasure."
  (decode "MVQXG5LSMUXA====") => "easure."
  (decode "MFZXK4TFFY======") => "asure."
  (decode "ON2XEZJO") => "sure."
  (decode "ME======") => (str "a")
  (decode "GEZDGNBV") => "12345"
  (decode "MFRGGZDF") => "abcde"
  (decode "GEZDGNBVGY3TQOJQ") => "1234567890"
  (decode "KR3WC4ZAMJZGS3DMNFTSYIDBNZSCA5DIMUQHG3DJORUHSIDUN53GK4Y=") => "Twas brillig, and the slithy toves"
  )


