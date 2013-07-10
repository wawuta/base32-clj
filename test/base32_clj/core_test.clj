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
      (encode "sure.") =>  "ON2XEZJO"
	 (encode "sure") =>  "ON2XEZI="
	 (encode "sur") =>  "ON2XE==="
	 (encode "su") =>  "ON2Q===="
	 (encode "leasure.") =>  "NRSWC43VOJSS4==="
	 (encode "easure.") =>  "MVQXG5LSMUXA===="
	 (encode "asure.") => "MFZXK4TFFY======"
	 (encode "sure.") =>  "ON2XEZJO"      
      (encode (str "a")) => "ME======"
      (encode "12345") => "GEZDGNBV"
      (encode "abcde") => "MFRGGZDF"
      (encode "1234567890") => "GEZDGNBVGY3TQOJQ"
      (encode "Twas brillig, and the slithy toves") => "KR3WC4ZAMJZGS3DMNFTSYIDBNZSCA5DIMUQHG3DJORUHSIDUN53GK4Y="
      (encode "We the people of the United States, in order to form a more perfect union,establish justice, insure domestic tranquility, provide for the common defense, promote the general welfare, and secure the blessings of liberty to ourselves and our posterity, do ordain and establish this Constitution for the United States of America.") => "EAQCAIBAEBLWKIDUNBSSA4DFN5YGYZJAN5TCA5DIMUQFK3TJORSWIICTORQXIZLTFQQGS3RAN5ZGIZLSEB2G6IDGN5ZG2IDBEBWW64TFEBYGK4TGMVRXIIDVNZUW63RMBIQCAIBAEAQGK43UMFRGY2LTNAQGU5LTORUWGZJMEBUW443VOJSSAZDPNVSXG5DJMMQHI4TBNZYXK2LMNF2HSLBAOBZG65TJMRSSAZTPOIQHI2DFEBRW63LNN5XAUIBAEAQCAIDEMVTGK3TTMUWCA4DSN5WW65DFEB2GQZJAM5SW4ZLSMFWCA53FNRTGC4TFFQQGC3TEEBZWKY3VOJSSA5DIMUQGE3DFONZWS3THOMQG6ZRANRUWEZLSOR4QUIBAEAQCAIDUN4QG65LSONSWY5TFOMQGC3TEEBXXK4RAOBXXG5DFOJUXI6JMEBSG6IDPOJSGC2LOEBQW4ZBAMVZXIYLCNRUXG2BAORUGS4ZAINXW443UNF2HK5DJN5XAUIBAEAQCAIDGN5ZCA5DIMUQFK3TJORSWIICTORQXIZLTEBXWMICBNVSXE2LDMEXAU==="
      )