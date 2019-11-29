/*
 * Copyright Levi Nine, 2019
 */
package com.levi9.hack9.reference2019.service;

import java.time.Instant;
import java.util.Optional;

import com.levi9.hack9.reference.api.model.Call;
import com.levi9.hack9.reference.api.model.CallCost;
import com.levi9.hack9.reference.api.model.Listing;
import com.levi9.hack9.reference.api.model.Price;

/**
 * Call related services.
 * 
 * @author n.milutinovic
 */
public interface CallService {
	/**
	 * Return price for the given number dialed and time of call.
	 * 
	 * @param called telephone number to dial.
	 * @param time time of the call.
	 * @return price of the call, or {@code null} if not found.
	 */
	Optional<Price> getPrice(String called, Instant time);
	/**
	 * Register a call that was made.
	 * 
	 * @param call call, with all its properties.
	 * @return cost of the call.
	 */
	Optional<CallCost> registerCall(Call call);
	/**
	 * Get the listing of number called from the given number in the
	 * given period of time.
	 * 
	 * @param caller the number for which we generate listing.
	 * @param from starting moment of listing period.
	 * @param to ending moment of listing period.
	 * @return listing for the given period, if found
	 */
	Listing getListing(String caller, Instant from, Instant to);
}
