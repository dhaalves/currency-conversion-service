package com.dhaalves.resources;

import com.dhaalves.model.Transaction;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.MethodProperties;
import io.quarkus.rest.data.panache.ResourceProperties;
import java.util.UUID;
import javax.ws.rs.core.Response;

/**
 * This interface expose all basic CRUD operations (persist, delete, update, find...) for the
 * Transaction entity as JAX-RS resource
 */
@ResourceProperties(path = "transaction")
public interface TransactionBaseResource extends PanacheEntityResource<Transaction, UUID> {

  @MethodProperties(exposed = false)
  Response add(Transaction transaction);
}
