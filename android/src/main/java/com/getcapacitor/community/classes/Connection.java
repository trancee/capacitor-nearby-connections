package com.getcapacitor.community.classes;

import androidx.annotation.NonNull;

public record Connection(@NonNull String authenticationToken, @NonNull Boolean isIncomingConnection) {}
