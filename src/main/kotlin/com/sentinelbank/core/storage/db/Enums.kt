package com.sentinelbank.core.storage.db

/** Debit or credit card. */
enum class CardType { DEBIT, CREDIT }

/** Direction of a ledger transaction. */
enum class TransactionType { DEBIT, CREDIT }

/** Coarse spend category used for mock statement/insights views. */
enum class TransactionCategory { FOOD, TRANSPORT, SHOPPING, BILLS, TRANSFER, OTHER }

/** Lifecycle status of a transaction. */
enum class TransactionStatus { PENDING, COMPLETED, FAILED }
