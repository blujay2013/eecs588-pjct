// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/google/protobuf/non_nested_extension.proto

package protobuf_unittest;

public final class NonNestedExtension {
  private NonNestedExtension() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registry.add(protobuf_unittest.NonNestedExtension.nonNestedExtension);
  }
  public interface MessageToBeExtendedOrBuilder extends
      com.google.protobuf.GeneratedMessage.
          ExtendableMessageOrBuilder<MessageToBeExtended> {
  }
  /**
   * Protobuf type {@code protobuf_unittest.MessageToBeExtended}
   */
  public static final class MessageToBeExtended extends
      com.google.protobuf.GeneratedMessage.ExtendableMessage<
        MessageToBeExtended> implements MessageToBeExtendedOrBuilder {
    // Use MessageToBeExtended.newBuilder() to construct.
    private MessageToBeExtended(com.google.protobuf.GeneratedMessage.ExtendableBuilder<protobuf_unittest.NonNestedExtension.MessageToBeExtended, ?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private MessageToBeExtended(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final MessageToBeExtended defaultInstance;
    public static MessageToBeExtended getDefaultInstance() {
      return defaultInstance;
    }

    public MessageToBeExtended getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private MessageToBeExtended(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return protobuf_unittest.NonNestedExtension.internal_static_protobuf_unittest_MessageToBeExtended_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return protobuf_unittest.NonNestedExtension.internal_static_protobuf_unittest_MessageToBeExtended_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              protobuf_unittest.NonNestedExtension.MessageToBeExtended.class, protobuf_unittest.NonNestedExtension.MessageToBeExtended.Builder.class);
    }

    public static com.google.protobuf.Parser<MessageToBeExtended> PARSER =
        new com.google.protobuf.AbstractParser<MessageToBeExtended>() {
      public MessageToBeExtended parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new MessageToBeExtended(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<MessageToBeExtended> getParserForType() {
      return PARSER;
    }

    private void initFields() {
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!extensionsAreInitialized()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      com.google.protobuf.GeneratedMessage
        .ExtendableMessage<protobuf_unittest.NonNestedExtension.MessageToBeExtended>.ExtensionWriter extensionWriter =
          newExtensionWriter();
      extensionWriter.writeUntil(536870912, output);
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      size += extensionsSerializedSize();
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static protobuf_unittest.NonNestedExtension.MessageToBeExtended parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protobuf_unittest.NonNestedExtension.MessageToBeExtended parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protobuf_unittest.NonNestedExtension.MessageToBeExtended parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protobuf_unittest.NonNestedExtension.MessageToBeExtended parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protobuf_unittest.NonNestedExtension.MessageToBeExtended parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static protobuf_unittest.NonNestedExtension.MessageToBeExtended parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static protobuf_unittest.NonNestedExtension.MessageToBeExtended parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static protobuf_unittest.NonNestedExtension.MessageToBeExtended parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static protobuf_unittest.NonNestedExtension.MessageToBeExtended parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static protobuf_unittest.NonNestedExtension.MessageToBeExtended parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(protobuf_unittest.NonNestedExtension.MessageToBeExtended prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code protobuf_unittest.MessageToBeExtended}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.ExtendableBuilder<
          protobuf_unittest.NonNestedExtension.MessageToBeExtended, Builder> implements protobuf_unittest.NonNestedExtension.MessageToBeExtendedOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return protobuf_unittest.NonNestedExtension.internal_static_protobuf_unittest_MessageToBeExtended_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return protobuf_unittest.NonNestedExtension.internal_static_protobuf_unittest_MessageToBeExtended_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                protobuf_unittest.NonNestedExtension.MessageToBeExtended.class, protobuf_unittest.NonNestedExtension.MessageToBeExtended.Builder.class);
      }

      // Construct using protobuf_unittest.NonNestedExtension.MessageToBeExtended.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return protobuf_unittest.NonNestedExtension.internal_static_protobuf_unittest_MessageToBeExtended_descriptor;
      }

      public protobuf_unittest.NonNestedExtension.MessageToBeExtended getDefaultInstanceForType() {
        return protobuf_unittest.NonNestedExtension.MessageToBeExtended.getDefaultInstance();
      }

      public protobuf_unittest.NonNestedExtension.MessageToBeExtended build() {
        protobuf_unittest.NonNestedExtension.MessageToBeExtended result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public protobuf_unittest.NonNestedExtension.MessageToBeExtended buildPartial() {
        protobuf_unittest.NonNestedExtension.MessageToBeExtended result = new protobuf_unittest.NonNestedExtension.MessageToBeExtended(this);
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof protobuf_unittest.NonNestedExtension.MessageToBeExtended) {
          return mergeFrom((protobuf_unittest.NonNestedExtension.MessageToBeExtended)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(protobuf_unittest.NonNestedExtension.MessageToBeExtended other) {
        if (other == protobuf_unittest.NonNestedExtension.MessageToBeExtended.getDefaultInstance()) return this;
        this.mergeExtensionFields(other);
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!extensionsAreInitialized()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        protobuf_unittest.NonNestedExtension.MessageToBeExtended parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (protobuf_unittest.NonNestedExtension.MessageToBeExtended) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      // @@protoc_insertion_point(builder_scope:protobuf_unittest.MessageToBeExtended)
    }

    static {
      defaultInstance = new MessageToBeExtended(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:protobuf_unittest.MessageToBeExtended)
  }

  public interface MyNonNestedExtensionOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
  }
  /**
   * Protobuf type {@code protobuf_unittest.MyNonNestedExtension}
   */
  public static final class MyNonNestedExtension extends
      com.google.protobuf.GeneratedMessage
      implements MyNonNestedExtensionOrBuilder {
    // Use MyNonNestedExtension.newBuilder() to construct.
    private MyNonNestedExtension(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private MyNonNestedExtension(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final MyNonNestedExtension defaultInstance;
    public static MyNonNestedExtension getDefaultInstance() {
      return defaultInstance;
    }

    public MyNonNestedExtension getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private MyNonNestedExtension(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return protobuf_unittest.NonNestedExtension.internal_static_protobuf_unittest_MyNonNestedExtension_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return protobuf_unittest.NonNestedExtension.internal_static_protobuf_unittest_MyNonNestedExtension_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              protobuf_unittest.NonNestedExtension.MyNonNestedExtension.class, protobuf_unittest.NonNestedExtension.MyNonNestedExtension.Builder.class);
    }

    public static com.google.protobuf.Parser<MyNonNestedExtension> PARSER =
        new com.google.protobuf.AbstractParser<MyNonNestedExtension>() {
      public MyNonNestedExtension parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new MyNonNestedExtension(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<MyNonNestedExtension> getParserForType() {
      return PARSER;
    }

    private void initFields() {
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static protobuf_unittest.NonNestedExtension.MyNonNestedExtension parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protobuf_unittest.NonNestedExtension.MyNonNestedExtension parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protobuf_unittest.NonNestedExtension.MyNonNestedExtension parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static protobuf_unittest.NonNestedExtension.MyNonNestedExtension parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static protobuf_unittest.NonNestedExtension.MyNonNestedExtension parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static protobuf_unittest.NonNestedExtension.MyNonNestedExtension parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static protobuf_unittest.NonNestedExtension.MyNonNestedExtension parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static protobuf_unittest.NonNestedExtension.MyNonNestedExtension parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static protobuf_unittest.NonNestedExtension.MyNonNestedExtension parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static protobuf_unittest.NonNestedExtension.MyNonNestedExtension parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(protobuf_unittest.NonNestedExtension.MyNonNestedExtension prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code protobuf_unittest.MyNonNestedExtension}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements protobuf_unittest.NonNestedExtension.MyNonNestedExtensionOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return protobuf_unittest.NonNestedExtension.internal_static_protobuf_unittest_MyNonNestedExtension_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return protobuf_unittest.NonNestedExtension.internal_static_protobuf_unittest_MyNonNestedExtension_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                protobuf_unittest.NonNestedExtension.MyNonNestedExtension.class, protobuf_unittest.NonNestedExtension.MyNonNestedExtension.Builder.class);
      }

      // Construct using protobuf_unittest.NonNestedExtension.MyNonNestedExtension.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return protobuf_unittest.NonNestedExtension.internal_static_protobuf_unittest_MyNonNestedExtension_descriptor;
      }

      public protobuf_unittest.NonNestedExtension.MyNonNestedExtension getDefaultInstanceForType() {
        return protobuf_unittest.NonNestedExtension.MyNonNestedExtension.getDefaultInstance();
      }

      public protobuf_unittest.NonNestedExtension.MyNonNestedExtension build() {
        protobuf_unittest.NonNestedExtension.MyNonNestedExtension result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public protobuf_unittest.NonNestedExtension.MyNonNestedExtension buildPartial() {
        protobuf_unittest.NonNestedExtension.MyNonNestedExtension result = new protobuf_unittest.NonNestedExtension.MyNonNestedExtension(this);
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof protobuf_unittest.NonNestedExtension.MyNonNestedExtension) {
          return mergeFrom((protobuf_unittest.NonNestedExtension.MyNonNestedExtension)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(protobuf_unittest.NonNestedExtension.MyNonNestedExtension other) {
        if (other == protobuf_unittest.NonNestedExtension.MyNonNestedExtension.getDefaultInstance()) return this;
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        protobuf_unittest.NonNestedExtension.MyNonNestedExtension parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (protobuf_unittest.NonNestedExtension.MyNonNestedExtension) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      // @@protoc_insertion_point(builder_scope:protobuf_unittest.MyNonNestedExtension)
    }

    static {
      defaultInstance = new MyNonNestedExtension(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:protobuf_unittest.MyNonNestedExtension)
  }

  public static final int NONNESTEDEXTENSION_FIELD_NUMBER = 1;
  /**
   * <code>extend .protobuf_unittest.MessageToBeExtended { ... }</code>
   */
  public static final
    com.google.protobuf.GeneratedMessage.GeneratedExtension<
      protobuf_unittest.NonNestedExtension.MessageToBeExtended,
      protobuf_unittest.NonNestedExtension.MyNonNestedExtension> nonNestedExtension = com.google.protobuf.GeneratedMessage
          .newFileScopedGeneratedExtension(
        protobuf_unittest.NonNestedExtension.MyNonNestedExtension.class,
        protobuf_unittest.NonNestedExtension.MyNonNestedExtension.getDefaultInstance());
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_protobuf_unittest_MessageToBeExtended_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_protobuf_unittest_MessageToBeExtended_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_protobuf_unittest_MyNonNestedExtension_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_protobuf_unittest_MyNonNestedExtension_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n.com/google/protobuf/non_nested_extensi" +
      "on.proto\022\021protobuf_unittest\"\037\n\023MessageTo" +
      "BeExtended*\010\010\001\020\200\200\200\200\002\"\026\n\024MyNonNestedExten" +
      "sion:k\n\022nonNestedExtension\022&.protobuf_un" +
      "ittest.MessageToBeExtended\030\001 \001(\0132\'.proto" +
      "buf_unittest.MyNonNestedExtension"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_protobuf_unittest_MessageToBeExtended_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_protobuf_unittest_MessageToBeExtended_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_protobuf_unittest_MessageToBeExtended_descriptor,
              new java.lang.String[] { });
          internal_static_protobuf_unittest_MyNonNestedExtension_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_protobuf_unittest_MyNonNestedExtension_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_protobuf_unittest_MyNonNestedExtension_descriptor,
              new java.lang.String[] { });
          nonNestedExtension.internalInit(descriptor.getExtensions().get(0));
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
