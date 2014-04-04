// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/protobuf/unittest_import.proto

package com.google.protobuf.test;

public final class UnittestImport {
  private UnittestImport() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  /**
   * Protobuf enum {@code protobuf_unittest_import.ImportEnum}
   */
  public enum ImportEnum
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>IMPORT_FOO = 7;</code>
     */
    IMPORT_FOO(0, 7),
    /**
     * <code>IMPORT_BAR = 8;</code>
     */
    IMPORT_BAR(1, 8),
    /**
     * <code>IMPORT_BAZ = 9;</code>
     */
    IMPORT_BAZ(2, 9),
    ;

    /**
     * <code>IMPORT_FOO = 7;</code>
     */
    public static final int IMPORT_FOO_VALUE = 7;
    /**
     * <code>IMPORT_BAR = 8;</code>
     */
    public static final int IMPORT_BAR_VALUE = 8;
    /**
     * <code>IMPORT_BAZ = 9;</code>
     */
    public static final int IMPORT_BAZ_VALUE = 9;


    public final int getNumber() { return value; }

    public static ImportEnum valueOf(int value) {
      switch (value) {
        case 7: return IMPORT_FOO;
        case 8: return IMPORT_BAR;
        case 9: return IMPORT_BAZ;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<ImportEnum>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<ImportEnum>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<ImportEnum>() {
            public ImportEnum findValueByNumber(int number) {
              return ImportEnum.valueOf(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.google.protobuf.test.UnittestImport.getDescriptor().getEnumTypes().get(0);
    }

    private static final ImportEnum[] VALUES = values();

    public static ImportEnum valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int index;
    private final int value;

    private ImportEnum(int index, int value) {
      this.index = index;
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:protobuf_unittest_import.ImportEnum)
  }

  public interface ImportMessageOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // optional int32 d = 1;
    /**
     * <code>optional int32 d = 1;</code>
     */
    boolean hasD();
    /**
     * <code>optional int32 d = 1;</code>
     */
    int getD();
  }
  /**
   * Protobuf type {@code protobuf_unittest_import.ImportMessage}
   */
  public static final class ImportMessage extends
      com.google.protobuf.GeneratedMessage
      implements ImportMessageOrBuilder {
    // Use ImportMessage.newBuilder() to construct.
    private ImportMessage(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private ImportMessage(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final ImportMessage defaultInstance;
    public static ImportMessage getDefaultInstance() {
      return defaultInstance;
    }

    public ImportMessage getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private ImportMessage(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
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
            case 8: {
              bitField0_ |= 0x00000001;
              d_ = input.readInt32();
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
      return com.google.protobuf.test.UnittestImport.internal_static_protobuf_unittest_import_ImportMessage_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.protobuf.test.UnittestImport.internal_static_protobuf_unittest_import_ImportMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.protobuf.test.UnittestImport.ImportMessage.class, com.google.protobuf.test.UnittestImport.ImportMessage.Builder.class);
    }

    public static com.google.protobuf.Parser<ImportMessage> PARSER =
        new com.google.protobuf.AbstractParser<ImportMessage>() {
      public ImportMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ImportMessage(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<ImportMessage> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // optional int32 d = 1;
    public static final int D_FIELD_NUMBER = 1;
    private int d_;
    /**
     * <code>optional int32 d = 1;</code>
     */
    public boolean hasD() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional int32 d = 1;</code>
     */
    public int getD() {
      return d_;
    }

    private void initFields() {
      d_ = 0;
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
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt32(1, d_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, d_);
      }
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

    public static com.google.protobuf.test.UnittestImport.ImportMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.google.protobuf.test.UnittestImport.ImportMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.google.protobuf.test.UnittestImport.ImportMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.google.protobuf.test.UnittestImport.ImportMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.google.protobuf.test.UnittestImport.ImportMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.google.protobuf.test.UnittestImport.ImportMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.google.protobuf.test.UnittestImport.ImportMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.google.protobuf.test.UnittestImport.ImportMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.google.protobuf.test.UnittestImport.ImportMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.google.protobuf.test.UnittestImport.ImportMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.google.protobuf.test.UnittestImport.ImportMessage prototype) {
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
     * Protobuf type {@code protobuf_unittest_import.ImportMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.google.protobuf.test.UnittestImport.ImportMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.google.protobuf.test.UnittestImport.internal_static_protobuf_unittest_import_ImportMessage_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.google.protobuf.test.UnittestImport.internal_static_protobuf_unittest_import_ImportMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.google.protobuf.test.UnittestImport.ImportMessage.class, com.google.protobuf.test.UnittestImport.ImportMessage.Builder.class);
      }

      // Construct using com.google.protobuf.test.UnittestImport.ImportMessage.newBuilder()
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
        d_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.google.protobuf.test.UnittestImport.internal_static_protobuf_unittest_import_ImportMessage_descriptor;
      }

      public com.google.protobuf.test.UnittestImport.ImportMessage getDefaultInstanceForType() {
        return com.google.protobuf.test.UnittestImport.ImportMessage.getDefaultInstance();
      }

      public com.google.protobuf.test.UnittestImport.ImportMessage build() {
        com.google.protobuf.test.UnittestImport.ImportMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.google.protobuf.test.UnittestImport.ImportMessage buildPartial() {
        com.google.protobuf.test.UnittestImport.ImportMessage result = new com.google.protobuf.test.UnittestImport.ImportMessage(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.d_ = d_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.google.protobuf.test.UnittestImport.ImportMessage) {
          return mergeFrom((com.google.protobuf.test.UnittestImport.ImportMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.google.protobuf.test.UnittestImport.ImportMessage other) {
        if (other == com.google.protobuf.test.UnittestImport.ImportMessage.getDefaultInstance()) return this;
        if (other.hasD()) {
          setD(other.getD());
        }
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
        com.google.protobuf.test.UnittestImport.ImportMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.google.protobuf.test.UnittestImport.ImportMessage) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // optional int32 d = 1;
      private int d_ ;
      /**
       * <code>optional int32 d = 1;</code>
       */
      public boolean hasD() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional int32 d = 1;</code>
       */
      public int getD() {
        return d_;
      }
      /**
       * <code>optional int32 d = 1;</code>
       */
      public Builder setD(int value) {
        bitField0_ |= 0x00000001;
        d_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 d = 1;</code>
       */
      public Builder clearD() {
        bitField0_ = (bitField0_ & ~0x00000001);
        d_ = 0;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:protobuf_unittest_import.ImportMessage)
    }

    static {
      defaultInstance = new ImportMessage(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:protobuf_unittest_import.ImportMessage)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_protobuf_unittest_import_ImportMessage_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_protobuf_unittest_import_ImportMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n%google/protobuf/unittest_import.proto\022" +
      "\030protobuf_unittest_import\032,google/protob" +
      "uf/unittest_import_public.proto\"\032\n\rImpor" +
      "tMessage\022\t\n\001d\030\001 \001(\005*<\n\nImportEnum\022\016\n\nIMP" +
      "ORT_FOO\020\007\022\016\n\nIMPORT_BAR\020\010\022\016\n\nIMPORT_BAZ\020" +
      "\tB\034\n\030com.google.protobuf.testH\001P\000"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_protobuf_unittest_import_ImportMessage_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_protobuf_unittest_import_ImportMessage_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_protobuf_unittest_import_ImportMessage_descriptor,
              new java.lang.String[] { "D", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.test.UnittestImportPublic.getDescriptor(),
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}