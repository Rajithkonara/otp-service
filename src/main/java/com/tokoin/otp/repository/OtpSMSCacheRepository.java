package com.tokoin.otp.repository;

import java.util.Optional;

public interface OtpSMSCacheRepository {

    void put(String key, Integer value);

    Optional<String> get(String key);

    void remove(String key);

}
